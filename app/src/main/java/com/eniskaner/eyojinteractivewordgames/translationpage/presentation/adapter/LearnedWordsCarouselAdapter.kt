package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.eniskaner.eyojinteractivewordgames.databinding.ItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.databinding.LearnedItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder.LearnedWordsCarouselViewHolder
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder.WordCarouselViewHolder

class LearnedWordsCarouselAdapter (
    private val carouselClickListener: CarouselClickListener
) : ListAdapter<UIWordCard, LearnedWordsCarouselViewHolder>(LearnedWordsCarouselDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnedWordsCarouselViewHolder {
        val binding = LearnedItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LearnedWordsCarouselViewHolder(binding = binding, carouselClickListener = carouselClickListener)
    }

    override fun onBindViewHolder(holder: LearnedWordsCarouselViewHolder, position: Int) {
        holder.bindListToViewPager(getItem(position))
    }
}