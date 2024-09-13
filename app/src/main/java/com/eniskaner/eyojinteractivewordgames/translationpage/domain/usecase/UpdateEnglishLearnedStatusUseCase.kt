package com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import javax.inject.Inject

class UpdateEnglishLearnedStatusUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {

    suspend operator fun invoke(wordName: String, isEnglishLearned: Boolean) =
        wordCardRepository.updateEnglishLearnedStatus(wordName = wordName, isEnglishLearned = isEnglishLearned)
}