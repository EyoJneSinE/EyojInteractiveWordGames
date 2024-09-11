package com.eniskaner.eyojinteractivewordgames.translationpage.data.repo

import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.PrefUtils
import com.eniskaner.eyojinteractivewordgames.common.util.Resource
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class WordCardRepositoryImpl @Inject constructor(
    private val prefUtils: PrefUtils
) : WordCardRepository {

    override fun getWordCards(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = prefUtils.getWordCards()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }

    override fun saveWordCards(cards: List<UIWordCard>) {
        prefUtils.saveWordCards(cards = cards)
    }

    override fun addWordCard(card: UIWordCard) {
        prefUtils.addWordCard(card = card)
    }

    override fun updateWordCard(updatedCard: UIWordCard) {
        prefUtils.updateWordCard(updatedCard = updatedCard)
    }

    override fun getLearnedWords(): Flow<Resource<List<UIWordCard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = prefUtils.getLearnedWords()
            emit(Resource.Success(data = response))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }
}
