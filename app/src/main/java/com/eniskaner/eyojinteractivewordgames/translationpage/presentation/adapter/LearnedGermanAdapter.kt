package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.eniskaner.eyojinteractivewordgames.databinding.LearnedGermanItemCarouselBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewholder.LearnedGermanViewHolder

class LearnedGermanAdapter(
    private val carouselClickListener: CarouselClickListener
) : ListAdapter<UIWordCard, LearnedGermanViewHolder>(LearnedWordsCarouselDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedGermanViewHolder {
        val binding =
            LearnedGermanItemCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LearnedGermanViewHolder(
            binding = binding,
            carouselClickListener = carouselClickListener
        )
    }

    override fun onBindViewHolder(holder: LearnedGermanViewHolder, position: Int) {
        holder.bindListToViewPager(getItem(position))
    }
}