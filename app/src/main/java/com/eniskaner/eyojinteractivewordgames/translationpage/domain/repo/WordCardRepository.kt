package com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo

import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import kotlinx.coroutines.flow.Flow

interface WordCardRepository {

    fun getWordCards(): Flow<Resource<List<UIWordCard>>>

    fun saveWordCards(cards: List<UIWordCard>)

    fun addWordCard(card: UIWordCard)

    fun updateWordCard(updatedCard: UIWordCard)

    fun getLearnedWords(): Flow<Resource<List<UIWordCard>>>
}
