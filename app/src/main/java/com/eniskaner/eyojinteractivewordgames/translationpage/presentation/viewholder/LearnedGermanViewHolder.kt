package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.databinding.ItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.databinding.LearnedGermanItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener

class LearnedGermanViewHolder(
    private val binding: LearnedGermanItemCarouselBinding,
    private val carouselClickListener: CarouselClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bindListToViewPager(item: UIWordCard) = with(binding) {
        textLearnedGermanWord.text = item.wordName
        val colorList = listOf(
            R.color.eyoj_primary,
            R.color.eyoj_inverse_primary,
            R.color.eyoj_primary_container,
            R.color.eyoj_secondary_container,
            R.color.eyoj_secondary,
            R.color.eyoj_tertiary,
            R.color.eyoj_tertiary_container
        )
        val color = ContextCompat.getColor(cardLearnedGermanWord.context, colorList[adapterPosition % colorList.size])
        cardLearnedGermanWord.setCardBackgroundColor(ColorStateList.valueOf(color))
        binding.cardLearnedGermanWord.setOnClickListener {
            carouselClickListener.wordCardClickListener(item = item)
        }
    }
}