package com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import javax.inject.Inject

class GetLearnedGermanWordsUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {

    suspend operator fun invoke() =
        wordCardRepository.getLearnedGermanWords()
}