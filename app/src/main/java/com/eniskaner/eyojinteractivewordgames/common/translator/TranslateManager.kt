package com.eniskaner.eyojinteractivewordgames.common.translator

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslateManager @Inject constructor() {

    //Locale.forLanguageTag("bg").displayLanguage

    private var isEnglishDownloaded: Boolean = false
    private var isGermanDownloaded: Boolean = false
    fun setUpLanguageModels() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.TURKISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()

        val turkEngTranslator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        turkEngTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                isEnglishDownloaded = true
            }
            .addOnFailureListener {
                isEnglishDownloaded = false
            }

        val options2 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.TURKISH)
            .setTargetLanguage(TranslateLanguage.GERMAN)
            .build()

        val turkGermanTranslator = Translation.getClient(options2)

        val conditions2 = DownloadConditions.Builder()
            .requireWifi()
            .build()

        turkGermanTranslator.downloadModelIfNeeded(conditions2)
            .addOnSuccessListener {
                isGermanDownloaded = true
            }
            .addOnFailureListener {
                isGermanDownloaded = false
            }
    }

    fun translate(
        word: String,
        sourceLanguage: String,
        targetLanguage: String,
        onTranslateSuccess:(String) -> Unit,
        onTranslateFail:() -> Unit
    ) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        val turkEngTranslator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        turkEngTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

        setUpLanguageModels()

        turkEngTranslator.translate(word)
            .addOnSuccessListener { translatedString ->
                onTranslateSuccess(translatedString)
            }
            .addOnFailureListener { _ ->
                onTranslateFail()
            }
    }
}
