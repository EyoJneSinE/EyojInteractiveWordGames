package com.eniskaner.eyojinteractivewordgames.translator.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translator.domain.repo.LanguageRepository
import javax.inject.Inject

class GetLanguageCodeUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {

    operator fun invoke() = languageRepository.getAllLanguages()
}