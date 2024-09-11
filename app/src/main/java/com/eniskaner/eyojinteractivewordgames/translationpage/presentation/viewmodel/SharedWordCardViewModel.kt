package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCardProvider
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.AddWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnedWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetWordCardsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.SaveWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.UpdateWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.state.WordCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedWordCardViewModel @Inject constructor(
    private val addWordCardUseCase: AddWordCardUseCase,
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
    private val getWordCardsUseCase: GetWordCardsUseCase,
    private val saveWordCardUseCase: SaveWordCardUseCase,
    private val updateWordCardUseCase: UpdateWordCardUseCase,
    private val uiWordCardProvider: UIWordCardProvider
): ViewModel() {

    private val _wordCardListState = MutableStateFlow(WordCardState())

    val wordCardListState = _wordCardListState.asStateFlow()

    private var jobGetWords : Job? = null
    private var jobGetLearnedEnglishWords: Job? = null

    init {
        saveWordCards()
        getWordCards()
        getLearnedWords()
    }

    private fun getWordCards() {
        jobGetWords?.cancel()
        jobGetWords = getWordCardsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update {
                        it.copy(wordCardsList = resource.data ?: emptyList())
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveWordCards() {
        saveWordCardUseCase.invoke(uiWordCardProvider.addWords())
        _wordCardListState.update {
            it.copy(wordCardsList = uiWordCardProvider.addWords())
        }
    }

    fun addWordCard(card: UIWordCard) {

    }

    fun updateWordCard(updatedCard: UIWordCard, isLearned: Boolean) {
        val updatedList = _wordCardListState.value.wordCardsList.map {
            if (it.wordName == updatedCard.wordName) {
                it.copy(isLearned = isLearned)
            } else it
        }
        updateWordCardUseCase.invoke(updatedCard = updatedCard)
        _wordCardListState.update { wordCardState ->
            wordCardState.copy(
                wordCardsList = updatedList.filter { !it.isLearned },
                learnedEnglishCardList = updatedList.filter { it.isLearned }
            )
        }
    }

    private fun getLearnedWords() {
        jobGetLearnedEnglishWords?.cancel()
        jobGetLearnedEnglishWords = getLearnedWordsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update {
                        it.copy(learnedEnglishCardList = resource.data ?: emptyList())
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}