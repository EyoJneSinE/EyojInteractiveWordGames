package com.eniskaner.eyojinteractivewordgames.translationpage.domain.usecase

import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import javax.inject.Inject

class UpdateWordCardUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {

    suspend operator fun invoke(updateWordCard: UIWordCard) =
        wordCardRepository.updateWordCard(updateWordCard = updateWordCard)
}
