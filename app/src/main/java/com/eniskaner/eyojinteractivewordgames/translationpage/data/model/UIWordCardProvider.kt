package com.eniskaner.eyojinteractivewordgames.translationpage.data.model

import javax.inject.Inject

class UIWordCardProvider @Inject constructor() {

    fun addWords(): List<UIWordCard> {
        val wordList = listOf(
            UIWordCard(wordName = "kapıyı tutmak"),
            UIWordCard(wordName = "sıkılmak"),
            UIWordCard(wordName = "öncelik"),
            UIWordCard(wordName = "araba"),
            UIWordCard(wordName = "uçak"),
            UIWordCard(wordName = "havada süzülmek"),
            UIWordCard(wordName = "keyif"),
            UIWordCard(wordName = "eğlenmek"),
            UIWordCard(wordName = "öğrenmek"),
            UIWordCard(wordName = "sergilemek"),
            UIWordCard(wordName = "kıskanmak"),
            UIWordCard(wordName = "söylemek"),
            UIWordCard(wordName = "miktar"),
            UIWordCard(wordName = "sürüş"),
            UIWordCard(wordName = "sürat"),
            UIWordCard(wordName = "ivme"),
            UIWordCard(wordName = "kazanç"),
            UIWordCard(wordName = "fiyat"),
            UIWordCard(wordName = "etiket"),
            UIWordCard(wordName = "kirletme"),
            UIWordCard(wordName = "çiçekleri sulamak"),
            UIWordCard(wordName = "çiçek"),
            UIWordCard(wordName = "gül"),
            UIWordCard(wordName = "saksı"),
            UIWordCard(wordName = "yonca"),
            UIWordCard(wordName = "şans"),
            UIWordCard(wordName = "fabrika"),
            UIWordCard(wordName = "üretim"),
            UIWordCard(wordName = "bölge"),
            UIWordCard(wordName = "söyleşi"),
            UIWordCard(wordName = "münazara"),
            UIWordCard(wordName = "saygı"),
            UIWordCard(wordName = "kader"),
            UIWordCard(wordName = "kıyafet"),
            UIWordCard(wordName = "rüzgar"),
            UIWordCard(wordName = "sersemlemiş hissetmek"),
            UIWordCard(wordName = "çıldırma"),
            UIWordCard(wordName = "sorun"),
            UIWordCard(wordName = "çözüm"),
            UIWordCard(wordName = "fikir"),
            UIWordCard(wordName = "gelişigüzel"),
            UIWordCard(wordName = "yağmur"),
            UIWordCard(wordName = "zürafa"),
            UIWordCard(wordName = "kanguru"),
            UIWordCard(wordName = "bateri"),
            UIWordCard(wordName = "kovmak"),
            UIWordCard(wordName = "lale"),
            UIWordCard(wordName = "bezelye"),
            UIWordCard(wordName = "kiriş"),
            UIWordCard(wordName = "duvar"),
            UIWordCard(wordName = "çift"),
            UIWordCard(wordName = "tek"),
            UIWordCard(wordName = "kiraz")
        )
        return wordList
    }
}