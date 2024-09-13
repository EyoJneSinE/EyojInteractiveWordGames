package com.eniskaner.eyojinteractivewordgames.translationpage.data.repo

import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.PrefUtils
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database.WordCardDao
import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class WordCardRepositoryImpl @Inject constructor(
    private val wordCardDao: WordCardDao
) : WordCardRepository {

    override fun getWordCards(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = wordCardDao.getWordCards()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }

    override suspend fun saveWordCards(cards: List<UIWordCard>) {
        wordCardDao.saveWordCards(addCards = cards)
    }

    override suspend fun addWordCard(card: UIWordCard) {
        wordCardDao.addWordCard(wordCard = card)
    }

    override suspend fun updateWordCard(updateWordCard: UIWordCard) {
        wordCardDao.updateWordCard(updateWordCard = updateWordCard)
    }

    override fun getLearnedWords(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = wordCardDao.getLearnedWords()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }

    override fun getLearnedGermanWords(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = wordCardDao.getLearnedGermanWords()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }

    override fun getLearnableWords(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = wordCardDao.getLearnableWords()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }

    override suspend fun updateGermanLearnedStatus(wordName: String, isGermanLearned: Boolean) {
        wordCardDao.updateGermanLearnedStatus(wordName = wordName, isGermanLearned = isGermanLearned)
    }

    override suspend fun updateEnglishLearnedStatus(wordName: String, isEnglishLearned: Boolean) {
        wordCardDao.updateEnglishLearnedStatus(wordName = wordName, isEnglishLearned = isEnglishLearned)
    }
}
