package com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.model.WordCardEntity
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

@Dao
interface WordCardDao {

    @Query("SELECT * FROM word_cards")
    suspend fun getWordCards(): List<UIWordCard>

    @Query("SELECT * FROM word_cards WHERE isEnglishLearned = 1")
    suspend fun getLearnedWords(): List<UIWordCard>

    @Query("SELECT * FROM word_cards WHERE isGermanLearned = 1")
    suspend fun getLearnedGermanWords(): List<UIWordCard>

    @Query("SELECT * FROM word_cards WHERE isEnglishLearned = 0 or isGermanLearned = 0")
    suspend fun getLearnableWords(): List<UIWordCard>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWordCard(wordCard: UIWordCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWordCards(addCards: List<UIWordCard>)

    @Query("UPDATE word_cards SET isEnglishLearned = :isEnglishLearned WHERE wordName = :wordName")
    suspend fun updateEnglishLearnedStatus(wordName: String, isEnglishLearned: Boolean)

    @Query("UPDATE word_cards SET isGermanLearned = :isGermanLearned WHERE wordName = :wordName")
    suspend fun updateGermanLearnedStatus(wordName: String, isGermanLearned: Boolean)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWordCard(updateWordCard: UIWordCard)

    @Delete
    suspend fun deleteWordCard(wordCard: UIWordCard)
}