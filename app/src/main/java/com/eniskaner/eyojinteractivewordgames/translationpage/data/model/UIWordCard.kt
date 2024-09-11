package com.eniskaner.eyojinteractivewordgames.translationpage.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UIWordCard(
    val wordName: String,
    val isLearned: Boolean = false,
): Parcelable
