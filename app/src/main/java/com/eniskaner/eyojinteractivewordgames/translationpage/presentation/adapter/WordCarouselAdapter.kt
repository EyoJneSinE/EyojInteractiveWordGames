package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.eniskaner.eyojinteractivewordgames.databinding.ItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder.WordCarouselViewHolder

class WordCarouselAdapter(
    private val carouselClickListener: CarouselClickListener
) : ListAdapter<UIWordCard, WordCarouselViewHolder>(WordCarouselDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordCarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordCarouselViewHolder(binding = binding, carouselClickListener = carouselClickListener)
    }

    override fun onBindViewHolder(holder: WordCarouselViewHolder, position: Int) {
        holder.bindListToViewPager(getItem(position))
    }
}
