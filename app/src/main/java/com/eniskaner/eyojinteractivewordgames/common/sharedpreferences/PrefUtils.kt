package com.eniskaner.eyojinteractivewordgames.common.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.Constants.WORD_CARDS_KEY
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefUtils @Inject constructor(@ApplicationContext private val context: Context) {



    private fun getPrefs(): SharedPreferences {
        return context.getSharedPreferences(WORD_CARDS_KEY, Context.MODE_PRIVATE)
    }

    fun getWordCards(): List<UIWordCard> {
        val prefs = getPrefs()
        val gson = Gson()
        val json = prefs.getString(WORD_CARDS_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<UIWordCard>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveWordCards(cards: List<UIWordCard>) {
        val prefs = getPrefs()
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(cards)
        editor.putString(WORD_CARDS_KEY, json).apply()
    }

    fun addWordCard(card: UIWordCard) {
        val cards = getWordCards().toMutableList()
        cards.add(card)
        saveWordCards(cards)
    }

    fun updateWordCard(updatedCard: UIWordCard) {
        val cards = getWordCards().map {
            if (it.wordName == updatedCard.wordName) updatedCard else it
        }
        saveWordCards(cards)
    }

    fun getLearnedWords(): List<UIWordCard> {
        val prefs = getPrefs()
        val gson = Gson()
        val json = prefs.getString(WORD_CARDS_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<UIWordCard>>() {}.type
        val allCards: List<UIWordCard> = gson.fromJson(json, type)

        return allCards.filter { it.isEnglishLearned }
    }
}