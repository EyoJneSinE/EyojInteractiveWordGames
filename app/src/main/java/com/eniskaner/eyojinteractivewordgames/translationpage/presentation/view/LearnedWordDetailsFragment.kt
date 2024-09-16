package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.base.BaseFragment
import com.eniskaner.eyojinteractivewordgames.common.translator.TranslateManager
import com.eniskaner.eyojinteractivewordgames.common.util.flipCardWithSetup
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLearnedWordDetailsBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LearnedWordDetailsFragment : BaseFragment<FragmentLearnedWordDetailsBinding>() {

    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private var isFront = true

    @Inject
    lateinit var translateManager: TranslateManager

    override fun setBinding(): FragmentLearnedWordDetailsBinding =
        FragmentLearnedWordDetailsBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)

        uiWordCard?.let { wordCard ->
            getLearnedWordDetailData(item = wordCard)
            binding.buttonTranslateEnglishFront.text = getString(R.string.button_text_english)
            binding.buttonTranslateEnglishFront.setOnClickListener {
                handleLanguageChange(
                    uiWordCard = wordCard,
                    isLearnedChecked = wordCard.isEnglishLearned,
                    sourceLanguage = TranslateLanguage.TURKISH,
                    targetLanguage = TranslateLanguage.ENGLISH,
                    buttonTextFront = getString(R.string.button_text_turkish),
                    buttonTextBack = getString(R.string.button_text_english),
                    isLanguageSelected = true,
                    onLearnedToggle = { isLearned ->
                        sharedWordCardViewModel.updateWordCard(
                            updatedCard = wordCard,
                            isEnglishLearned = isLearned,
                            isGermanLearned = wordCard.isGermanLearned
                        )
                    }
                )
            }
        }
    }

    private fun handleLanguageChange(
        uiWordCard: UIWordCard,
        isLearnedChecked: Boolean,
        sourceLanguage: String,
        targetLanguage: String,
        isLanguageSelected: Boolean,
        onLearnedToggle: (Boolean) -> Unit,
        buttonTextFront: String,
        buttonTextBack: String
    ) {
        binding.buttonTranslateEnglishFront.text = buttonTextBack
        setupTranslationAndAnimation(
            uiWordCard = uiWordCard,
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            buttonTextFront = buttonTextFront,
            buttonTextBack = buttonTextBack
        )
        binding.switchIsEnglishLearned.text =
            if (isLanguageSelected) getString(R.string.is_english_learned) else getString(R.string.is_german_learned)
        binding.switchIsEnglishLearned.isChecked = isLearnedChecked
        binding.switchIsEnglishLearned.setOnClickListener {
            onLearnedToggle(!isLearnedChecked)
        }
    }

    private fun setupTranslationAndAnimation(
        uiWordCard: UIWordCard,
        sourceLanguage: String,
        targetLanguage: String,
        buttonTextFront: String,
        buttonTextBack: String
    ) {
        binding.buttonTranslateEnglishFront.text =
            if (isFront) buttonTextFront else buttonTextBack

        isFront = binding.cardWordFront.flipCardWithSetup(
            frontView = binding.cardWordFront,
            backView = binding.cardWordBack,
            context = requireContext(),
            isFront = isFront,
            onFrontFlipAction = {
                translateManager.translate(
                    word = uiWordCard.wordName,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    onTranslateSuccess = { translatedString ->
                        binding.textWordBack.text = translatedString
                    },
                    onTranslateFail = {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.translation_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }

    private fun getLearnedWordDetailData(item: UIWordCard) = with(binding) {
        textWordFront.text = item.wordName
    }

}