package com.eniskaner.eyojinteractivewordgames.translator.data.repo

import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel
import com.eniskaner.eyojinteractivewordgames.translator.domain.repo.LanguageRepository
import com.google.mlkit.nl.translate.TranslateLanguage

class LanguageRepositoryImpl : LanguageRepository {
    override fun getAllLanguages(): List<LanguageModel> {
        val allLanguages = TranslateLanguage.getAllLanguages()
        val languageModels = allLanguages.map { languageCode ->
            LanguageModel(languageName = languageCode)
        }
        return languageModels
    }
}