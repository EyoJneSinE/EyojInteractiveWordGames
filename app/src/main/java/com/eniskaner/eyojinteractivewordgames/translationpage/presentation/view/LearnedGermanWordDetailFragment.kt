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
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLearnedGermanWordDetailBinding
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
class LearnedGermanWordDetailFragment : BaseFragment<FragmentLearnedGermanWordDetailBinding>() {

    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private lateinit var back_anim: AnimatorSet
    private lateinit var front_anim: AnimatorSet
    var isFront = true
    var isDownloaded: Boolean = false
    var isGerman: Boolean = true

    @Inject
    lateinit var translateManager: TranslateManager

    override fun setBinding(): FragmentLearnedGermanWordDetailBinding =
        FragmentLearnedGermanWordDetailBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)

        uiWordCard?.let { wordCard ->
            getLearnedWordDetailData(item = wordCard)
            binding.buttonTranslateGermanFront.text = getString(R.string.button_text_german)
            binding.buttonTranslateGermanFront.setOnClickListener {
                handleLanguageChange(
                    uiWordCard = wordCard,
                    isLearnedChecked = wordCard.isGermanLearned,
                    sourceLanguage = TranslateLanguage.TURKISH,
                    targetLanguage = TranslateLanguage.GERMAN,
                    buttonTextFront = getString(R.string.button_text_turkish),
                    buttonTextBack = getString(R.string.button_text_german),
                    isLanguageSelected = false,
                    onLearnedToggle = { isLearned ->
                        sharedWordCardViewModel.updateWordCard(
                            updatedCard = wordCard,
                            isEnglishLearned = wordCard.isEnglishLearned,
                            isGermanLearned = isLearned
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
        binding.buttonTranslateGermanFront.text = buttonTextBack
        setupTranslationAndAnimation(
            uiWordCard = uiWordCard,
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            buttonTextFront = buttonTextFront,
            buttonTextBack = buttonTextBack
        )
        binding.switchIsGermanLearned.text =
            if (isLanguageSelected) getString(R.string.is_english_learned) else getString(R.string.is_german_learned)
        binding.switchIsGermanLearned.isChecked = isLearnedChecked
        binding.switchIsGermanLearned.setOnClickListener {
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
        binding.buttonTranslateGermanFront.text =
            if (isFront) buttonTextFront else buttonTextBack

        isFront = binding.cardWordGermanFront.flipCardWithSetup(
            frontView = binding.cardWordGermanFront,
            backView = binding.cardWordGermanBack,
            context = requireContext(),
            isFront = isFront,
            onFrontFlipAction = {
                translateManager.translate(
                    word = uiWordCard.wordName,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    onTranslateSuccess = { translatedString ->
                        binding.textGermanWordBack.text = translatedString
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
        textWordGermanFront.text = item.wordName
    }

}