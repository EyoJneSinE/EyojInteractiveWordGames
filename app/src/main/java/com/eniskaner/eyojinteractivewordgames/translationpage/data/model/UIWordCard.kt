package com.eniskaner.eyojinteractivewordgames.translationpage.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "word_cards")
data class UIWordCard(
    @PrimaryKey val wordName: String,
    val isEnglishLearned: Boolean,
    val isGermanLearned: Boolean
): Parcelable
