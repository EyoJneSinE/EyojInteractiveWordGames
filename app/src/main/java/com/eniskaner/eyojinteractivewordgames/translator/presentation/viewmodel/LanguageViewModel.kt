package com.eniskaner.eyojinteractivewordgames.translator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.state.WordCardState
import com.eniskaner.eyojinteractivewordgames.translator.domain.usecase.GetLanguageCodeUseCase
import com.eniskaner.eyojinteractivewordgames.translator.presentation.LanguageCodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LanguageViewModel @Inject constructor(
    private val getLanguageCodeUseCase: GetLanguageCodeUseCase
): ViewModel() {

    private val _languageCodeState = MutableStateFlow(LanguageCodeState())
    val languageCodeState = _languageCodeState.asStateFlow()

    fun getLanguageCodes() {
        val languageCodeList = getLanguageCodeUseCase.invoke()
        _languageCodeState.update {
            it.copy(languageCodes = languageCodeList)
        }

    }
}