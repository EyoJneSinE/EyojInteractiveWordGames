package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.databinding.ItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener

class WordCarouselViewHolder(
    private val binding: ItemCarouselBinding,
    private val carouselClickListener: CarouselClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bindListToViewPager(item: UIWordCard) = with(binding) {
        textWord.text = item.wordName
        val colorList = listOf(
            R.color.eyoj_primary,
            R.color.eyoj_inverse_primary,
            R.color.eyoj_primary_container,
            R.color.eyoj_secondary_container,
            R.color.eyoj_secondary,
            R.color.eyoj_tertiary,
            R.color.eyoj_tertiary_container
        )
        val color = ContextCompat.getColor(cardWord.context, colorList[adapterPosition % colorList.size])
        cardWord.setCardBackgroundColor(ColorStateList.valueOf(color))
        binding.cardWord.setOnClickListener {
            carouselClickListener.wordCardClickListener(item = item)
        }
    }
}