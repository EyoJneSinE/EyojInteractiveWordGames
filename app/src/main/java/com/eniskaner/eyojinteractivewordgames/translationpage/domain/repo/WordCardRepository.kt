package com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo

import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import kotlinx.coroutines.flow.Flow

interface WordCardRepository {

    fun getWordCards(): Flow<Resource<List<UIWordCard>>>

    suspend fun saveWordCards(cards: List<UIWordCard>)

    suspend fun addWordCard(card: UIWordCard)

    suspend fun updateWordCard(updateWordCard: UIWordCard)

    fun getLearnedWords(): Flow<Resource<List<UIWordCard>>>

    fun getLearnedGermanWords(): Flow<Resource<List<UIWordCard>>>

    fun getLearnableWords(): Flow<Resource<List<UIWordCard>>>

    suspend fun updateGermanLearnedStatus(wordName: String, isGermanLearned: Boolean)

    suspend fun updateEnglishLearnedStatus(wordName: String, isEnglishLearned: Boolean)
}
