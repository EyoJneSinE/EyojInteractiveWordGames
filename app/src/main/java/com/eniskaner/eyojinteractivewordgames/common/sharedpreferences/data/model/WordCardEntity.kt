package com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_cards")
data class WordCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val wordName: String,
    val isEnglishLearned: Boolean = false,
    val isGermanLearned: Boolean = false
)
