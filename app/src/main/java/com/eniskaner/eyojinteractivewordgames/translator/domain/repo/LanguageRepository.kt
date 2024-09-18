package com.eniskaner.eyojinteractivewordgames.translator.domain.repo

import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel

interface LanguageRepository {

    fun getAllLanguages(): List<LanguageModel>
}