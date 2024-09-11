package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter

import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

interface CarouselClickListener {

    fun wordCardClickListener(item: UIWordCard)
}