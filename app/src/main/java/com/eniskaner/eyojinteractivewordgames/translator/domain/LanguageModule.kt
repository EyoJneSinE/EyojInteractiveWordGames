package com.eniskaner.eyojinteractivewordgames.translator.domain

import com.eniskaner.eyojinteractivewordgames.translator.data.repo.LanguageRepositoryImpl
import com.eniskaner.eyojinteractivewordgames.translator.domain.repo.LanguageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LanguageModule {

    @Provides
    @Singleton
    fun provideLanguageRepository(): LanguageRepository = LanguageRepositoryImpl()
}