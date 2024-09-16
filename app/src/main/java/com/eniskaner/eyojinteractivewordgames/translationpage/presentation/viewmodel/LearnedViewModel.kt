package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnedGermanWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase.GetLearnedWordsUseCase
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.state.WordCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LearnedViewModel @Inject constructor(
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
    private val getLearnedGermanWordsUseCase: GetLearnedGermanWordsUseCase
) : ViewModel() {

    private val _wordCardListState = MutableStateFlow(WordCardState())
    val wordCardListState = _wordCardListState.asStateFlow()


    suspend fun getLearnedEnglishWords() {
        getLearnedWordsUseCase.invoke().onEach {resource ->
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

    fun shuffleEnglishWords(){
        val words = _wordCardListState.value.learnedEnglishCardList
        _wordCardListState.update {
            it.copy(learnedEnglishCardList = words.shuffled())
        }
    }

    suspend fun getLearnedGermanWords() {
        getLearnedGermanWordsUseCase.invoke().onEach {resource ->
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

    fun shuffleGermanWords(){
        val words = _wordCardListState.value.learnedGermanCardList
        _wordCardListState.update {
            it.copy(learnedGermanCardList = words.shuffled())
        }
    }
}