package com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel

class LanguageCodeDiffCallBack: DiffUtil.ItemCallback<LanguageModel>() {
    override fun areItemsTheSame(
        oldItem: LanguageModel,
        newItem: LanguageModel
    ): Boolean = oldItem.languageName == newItem.languageName

    override fun areContentsTheSame(
        oldItem: LanguageModel,
        newItem: LanguageModel
    ): Boolean = oldItem == newItem
}