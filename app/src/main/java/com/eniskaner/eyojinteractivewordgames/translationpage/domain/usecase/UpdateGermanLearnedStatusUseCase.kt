package com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import javax.inject.Inject

class UpdateGermanLearnedStatusUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {

    suspend operator fun invoke(wordName: String, isGermanLearned: Boolean) =
        wordCardRepository.updateGermanLearnedStatus(wordName = wordName, isGermanLearned = isGermanLearned)
}