package com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter

import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel

interface LanguageClickListener {

    fun languageCodeClickListener(item: LanguageModel, position: Int)
}
