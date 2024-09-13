package com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.domain.di

import android.content.Context
import androidx.room.Room
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database.WordCardDao
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database.WordCardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WordCardDatabase {

        return Room.databaseBuilder(
            context,
            WordCardDatabase::class.java,
            "word_card_database"
        ).build()
    }

    @Provides
    fun provideWordCardDao(database: WordCardDatabase): WordCardDao {
        return database.wordCardDao()
    }
}