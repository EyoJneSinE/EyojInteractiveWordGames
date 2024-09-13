package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.state

import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

data class WordCardState(
    val wordCardsList: List<UIWordCard> = emptyList(),
    val learnedEnglishCardList: List<UIWordCard> = emptyList(),
    val learnedGermanCardList: List<UIWordCard> = emptyList()
)
