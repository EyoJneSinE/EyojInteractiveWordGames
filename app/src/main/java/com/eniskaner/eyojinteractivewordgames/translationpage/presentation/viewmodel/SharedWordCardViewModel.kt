package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCardProvider
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.AddWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnableWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnedGermanWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnedWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetWordCardsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.SaveWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.UpdateEnglishLearnedStatusUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.UpdateGermanLearnedStatusUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.UpdateWordCardUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.state.WordCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedWordCardViewModel @Inject constructor(
    private val addWordCardUseCase: AddWordCardUseCase,
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
    private val getWordCardsUseCase: GetWordCardsUseCase,
    private val saveWordCardUseCase: SaveWordCardUseCase,
    private val updateWordCardUseCase: UpdateWordCardUseCase,
    private val uiWordCardProvider: UIWordCardProvider,
    private val getLearnableWordsUseCase: GetLearnableWordsUseCase,
    private val updateEnglishLearnedStatusUseCase: UpdateEnglishLearnedStatusUseCase,
    private val updateGermanLearnedStatusUseCase: UpdateGermanLearnedStatusUseCase,
    private val getLearnedGermanWordsUseCase: GetLearnedGermanWordsUseCase
) : ViewModel() {

    private val _wordCardListState = MutableStateFlow(WordCardState())

    val wordCardListState = _wordCardListState.asStateFlow()

    private var jobGetWords: Job? = null
    private var jobGetLearnedEnglishWords: Job? = null
    private var jobGetLearnableEnglishWords: Job? = null
    private var jobGetLearnedGermanWords: Job? = null

    fun getWordCards() {
        jobGetWords?.cancel()
        jobGetWords = getWordCardsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update { state ->
                        state.copy(
                            wordCardsList = resource.data ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun saveWordCards() {
        viewModelScope.launch {
            if (_wordCardListState.value.wordCardsList.isEmpty()) {
                saveWordCardUseCase.invoke(uiWordCardProvider.addWords())
                _wordCardListState.update {
                    it.copy(wordCardsList = uiWordCardProvider.addWords())
                }
            }
        }
    }

    fun shuffleWords(){
        val words = _wordCardListState.value.wordCardsList
        _wordCardListState.update {
            it.copy(wordCardsList = words.shuffled())
        }
    }

    fun addWordCard(card: UIWordCard) {}

    fun updateWordCard(
        updatedCard: UIWordCard,
        isEnglishLearned: Boolean,
        isGermanLearned: Boolean
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateWordCardUseCase.invoke(
                    updateWordCard = updatedCard.copy(
                        wordName = updatedCard.wordName,
                        isEnglishLearned = isEnglishLearned,
                        isGermanLearned = isGermanLearned
                    )
                )
                _wordCardListState.update { state ->
                    val updatedWordCardsList = state.wordCardsList.map { card ->
                        if (card.wordName == updatedCard.wordName) {
                            card.copy(
                                isEnglishLearned = isEnglishLearned,
                                isGermanLearned = isGermanLearned
                            )
                        } else {
                            card
                        }
                    }
                    state.copy(
                        wordCardsList = updatedWordCardsList,
                        learnedEnglishCardList = updatedWordCardsList.filter { it.isEnglishLearned },
                        learnedGermanCardList = updatedWordCardsList.filter { it.isGermanLearned }
                    )
                }
            }
        }
    }

    suspend fun getLearnableWords() {
        jobGetLearnableEnglishWords?.cancel()
        jobGetLearnableEnglishWords = getLearnableWordsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update { state ->
                        state.copy(wordCardsList = resource.data ?: emptyList())
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getLearnedEnglishWords() {
        jobGetLearnedEnglishWords?.cancel()
        jobGetLearnedEnglishWords = getLearnedWordsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update { state ->
                        state.copy(learnedEnglishCardList = resource.data ?: emptyList())
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getLearnedGermanWords() {
        jobGetLearnedGermanWords?.cancel()
        jobGetLearnedGermanWords = getLearnedGermanWordsUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _wordCardListState.update { state ->
                        state.copy(learnedGermanCardList = resource.data ?: emptyList())
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
