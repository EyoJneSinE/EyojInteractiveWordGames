package com.eniskaner.eyojinteractivewordgames.translator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eniskaner.eyojinteractivewordgames.translator.domain.usecase.GetLanguageCodeUseCase
import javax.inject.Inject

class LanguageViewModel @Inject constructor(
    private val getLanguageCodeUseCase: GetLanguageCodeUseCase
): ViewModel() {
}