package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

class LearnedWordsCarouselDiffCallBack: DiffUtil.ItemCallback<UIWordCard>() {
    override fun areItemsTheSame(
        oldItem: UIWordCard,
        newItem: UIWordCard
    ): Boolean =  oldItem == newItem

    override fun areContentsTheSame(
        oldItem: UIWordCard,
        newItem: UIWordCard
    ): Boolean = oldItem == newItem
}