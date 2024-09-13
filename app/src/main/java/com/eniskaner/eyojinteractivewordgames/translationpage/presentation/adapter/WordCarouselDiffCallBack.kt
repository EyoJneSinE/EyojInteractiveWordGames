package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

class WordCarouselDiffCallBack: DiffUtil.ItemCallback<UIWordCard>() {
    override fun areItemsTheSame(
        oldItem: UIWordCard,
        newItem: UIWordCard
    ): Boolean = oldItem.wordName == newItem.wordName ||
            oldItem.isEnglishLearned == newItem.isEnglishLearned ||
            oldItem.isGermanLearned == newItem.isGermanLearned

    override fun areContentsTheSame(
        oldItem: UIWordCard,
        newItem: UIWordCard
    ): Boolean = oldItem == newItem
}