package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.base.BaseFragment
import com.eniskaner.eyojinteractivewordgames.common.translator.TranslateManager
import com.eniskaner.eyojinteractivewordgames.common.util.flipCardWithSetup
import com.eniskaner.eyojinteractivewordgames.common.util.viewBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordDetailBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordListBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordDetailFragment : Fragment() {

    private val binding by viewBinding(FragmentWordDetailBinding::bind)
    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private var isFront = true

    @Inject
    lateinit var translateManager: TranslateManager

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)

        binding.buttonTranslateEnglishFront.text = getString(R.string.select_flag)

        uiWordCard?.let { wordCard ->
            getWordDetailData(item = wordCard)
            binding.buttonEnglish.setOnClickListener {
                handleLanguageChange(
                    uiWordCard = uiWordCard,
                    isLearnedChecked = wordCard.isEnglishLearned,
                    sourceLanguage = TranslateLanguage.TURKISH,
                    targetLanguage = TranslateLanguage.ENGLISH,
                    isLanguageSelected = true,
                    buttonTextFront = getString(R.string.button_text_turkish),
                    buttonTextBack = getString(R.string.button_text_english),
                    onLearnedToggle = { isLearned ->
                        sharedWordCardViewModel.updateWordCard(
                            updatedCard = wordCard,
                            isEnglishLearned = isLearned,
                            isGermanLearned = uiWordCard.isGermanLearned
                        )
                    }
                )
            }
            binding.buttonGerman.setOnClickListener {
                handleLanguageChange(
                    uiWordCard = uiWordCard,
                    isLearnedChecked = wordCard.isGermanLearned,
                    sourceLanguage = TranslateLanguage.TURKISH,
                    targetLanguage = TranslateLanguage.GERMAN,
                    isLanguageSelected = false,
                    buttonTextFront = getString(R.string.button_text_turkish),
                    buttonTextBack = getString(R.string.button_text_german),
                    onLearnedToggle = { isLearned ->
                        sharedWordCardViewModel.updateWordCard(
                            updatedCard = wordCard,
                            isEnglishLearned = uiWordCard.isEnglishLearned,
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
        with(binding) {
            buttonTranslateEnglishFront.text = buttonTextBack
            setupTranslationAndAnimation(
                uiWordCard = uiWordCard,
                sourceLanguage = sourceLanguage,
                targetLanguage = targetLanguage,
                buttonTextFront = buttonTextFront,
                buttonTextBack = buttonTextBack
            )
            switchIsEnglishLearned.text =
                if (isLanguageSelected) getString(R.string.is_english_learned) else getString(R.string.is_german_learned)
            switchIsEnglishLearned.isChecked = isLearnedChecked
            switchIsEnglishLearned.setOnClickListener {
                onLearnedToggle(!isLearnedChecked)
            }
        }
    }

    private fun setupTranslationAndAnimation(
        uiWordCard: UIWordCard,
        sourceLanguage: String,
        targetLanguage: String,
        buttonTextFront: String,
        buttonTextBack: String
    ) {
        with(binding) {
            buttonTranslateEnglishFront.setOnClickListener {
                buttonTranslateEnglishFront.text =
                    if (isFront) buttonTextFront else buttonTextBack

                isFront = cardWordFront.flipCardWithSetup(
                    frontView = cardWordFront,
                    backView = cardWordBack,
                    context = requireContext(),
                    isFront = isFront,
                    onFrontFlipAction = {
                        translateManager.translate(
                            word = uiWordCard.wordName,
                            sourceLanguage = sourceLanguage,
                            targetLanguage = targetLanguage,
                            onTranslateSuccess = { translatedString ->
                                textWordBack.text = translatedString
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
        }
    }

    private fun getWordDetailData(item: UIWordCard) = with(binding) {
        textWordFront.text = item.wordName
    }

}