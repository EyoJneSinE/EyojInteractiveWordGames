package com.eniskaner.eyojinteractivewordgames.translationpage.domain.di

import android.content.Context
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.PrefUtils
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database.WordCardDao
import com.eniskaner.eyojinteractivewordgames.translationpage.data.repo.WordCardRepositoryImpl
import com.eniskaner.eyojinteractivewordgames.translationpage.domain.repo.WordCardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun providePrefUtils(@ApplicationContext context: Context): PrefUtils {
        return PrefUtils(context = context)
    }

    @Provides
    @Singleton
    fun provideWordCardRepository(wordCardDao: WordCardDao): WordCardRepository {
        return WordCardRepositoryImpl(wordCardDao = wordCardDao)
    }
}