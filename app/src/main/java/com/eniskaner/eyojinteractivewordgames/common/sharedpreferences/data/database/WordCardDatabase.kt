package com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.data.model.WordCardEntity
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard

@Database(entities = [UIWordCard::class], version = 1, exportSchema = true)
abstract class WordCardDatabase : RoomDatabase() {
    abstract fun wordCardDao(): WordCardDao

    @Volatile
    private var INSTANCE: WordCardDatabase? = null

    fun getDatabase(
        context: Context
    ): WordCardDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                WordCardDatabase::class.java,
                "word_card_database"
            )
                .build()
            INSTANCE = instance
            instance
        }
    }
}