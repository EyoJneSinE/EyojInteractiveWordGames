package com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import javax.inject.Inject

class GetWordCardsUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {

    operator fun invoke() =
        wordCardRepository.getWordCards()
}
