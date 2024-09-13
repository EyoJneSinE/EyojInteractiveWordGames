package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.base.BaseFragment
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordDetailBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordDetailFragment : BaseFragment<FragmentWordDetailBinding>() {

    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private lateinit var back_anim: AnimatorSet
    private lateinit var front_anim: AnimatorSet
    var isFront = true
    var  isDownloaded:Boolean = false
    var isEnglish: Boolean = true
    var isGerman: Boolean = true

    override fun setBinding(): FragmentWordDetailBinding =
        FragmentWordDetailBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)
        uiWordCard?.let {
            getWordDetailData(it)
        }

        binding.buttonTranslateEnglishFront.text = "Çeviri için bayrak seç"


        binding.buttonEnglish.setOnClickListener {
            binding.buttonTranslateEnglishFront.text = "İngilizce Çeviri için"
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.TURKISH)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()

            launchAndRepeatWithViewLifecycle {
                launch {
                    val turkEngTranslator = Translation.getClient(options)

                    var conditions = DownloadConditions.Builder()
                        .requireWifi()
                        .build()
                    turkEngTranslator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            isDownloaded = true
                        }
                        .addOnFailureListener {
                            isDownloaded = false
                        }
                    val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)
                    uiWordCard?.let {
                        binding.switchIsEnglishLearned.isChecked = uiWordCard.isEnglishLearned
                        getWordDetailData(it)
                        binding.switchIsEnglishLearned.setOnCheckedChangeListener { _, isChecked ->
                            launchAndRepeatWithViewLifecycle {
                                launch {
                                    sharedWordCardViewModel.updateWordCard(updatedCard = UIWordCard(wordName = uiWordCard.wordName, isEnglishLearned = isChecked, isGermanLearned = false), isEnglishLearned = isChecked, isGermanLearned = false)
                                }
                            }
                            binding.switchIsEnglishLearned.isChecked = isChecked
                        }
                    }
                    val scale: Float = requireContext().resources.displayMetrics.density

                    binding.cardWordFront.cameraDistance = 14000f * scale
                    binding.cardWordBack.cameraDistance = 14000f * scale

                    front_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.front_animator) as AnimatorSet
                    back_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet

                    binding.buttonTranslateEnglishFront.setOnClickListener {
                        if (isEnglish) {
                            binding.buttonTranslateEnglishFront.text = "Türkçe Çevirisi"
                            isEnglish = false
                        } else {
                            binding.buttonTranslateEnglishFront.text = "İngilizce Çevirisi"
                            isEnglish = true
                        }
                        if (isFront) {
                            front_anim.setTarget(binding.cardWordFront)
                            back_anim.setTarget(binding.cardWordBack)
                            uiWordCard?.let {
                                if (isDownloaded) {
                                    turkEngTranslator.translate(uiWordCard.wordName)
                                        .addOnSuccessListener { traslatedString ->
                                            binding.textWordBack.text = traslatedString

                                        }
                                        .addOnFailureListener { exception ->
                                        }
                                } else {
                                    Toast.makeText(requireContext(), "model is not downloaded", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            front_anim.start()
                            back_anim.start()
                            isFront = false
                        } else {
                            front_anim.setTarget(binding.cardWordBack)
                            back_anim.setTarget(binding.cardWordFront)
                            front_anim.start()
                            back_anim.start()
                            isFront = true
                        }
                    }
                }
            }
        }

        binding.buttonGerman.setOnClickListener {
            binding.buttonTranslateEnglishFront.text = "Almanca Çeviri için"
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.TURKISH)
                .setTargetLanguage(TranslateLanguage.GERMAN)
                .build()

            launchAndRepeatWithViewLifecycle {
                launch {
                    val turkGermanTranslator = Translation.getClient(options)

                    var conditions = DownloadConditions.Builder()
                        .requireWifi()
                        .build()
                    turkGermanTranslator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            isDownloaded = true
                        }
                        .addOnFailureListener {
                            isDownloaded = false
                        }
                    val uiWordCard: UIWordCard? = arguments?.getParcelable("uiWordCard", UIWordCard::class.java)
                    uiWordCard?.let {
                        binding.switchIsEnglishLearned.text = "is German Learned"
                        binding.switchIsEnglishLearned.isChecked = uiWordCard.isEnglishLearned
                        getWordDetailData(it)
                        binding.switchIsEnglishLearned.setOnCheckedChangeListener { _, isChecked ->
                            launchAndRepeatWithViewLifecycle {
                                launch {
                                    sharedWordCardViewModel.updateWordCard(updatedCard = UIWordCard(wordName = uiWordCard.wordName, isEnglishLearned = false, isGermanLearned = isChecked), isEnglishLearned = false, isGermanLearned = isChecked)
                                }
                            }
                            binding.switchIsEnglishLearned.isChecked = isChecked
                        }
                    }
                    val scale: Float = requireContext().resources.displayMetrics.density

                    binding.cardWordFront.cameraDistance = 14000f * scale
                    binding.cardWordBack.cameraDistance = 14000f * scale

                    front_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.front_animator) as AnimatorSet
                    back_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet

                    binding.buttonTranslateEnglishFront.setOnClickListener {
                        if (isGerman) {
                            binding.buttonTranslateEnglishFront.text = "Türkçe Çevirisi"
                            isGerman = false
                        } else {
                            binding.buttonTranslateEnglishFront.text = "Almanca Çevirisi"
                            isGerman = true
                        }
                        if (isFront) {
                            front_anim.setTarget(binding.cardWordFront)
                            back_anim.setTarget(binding.cardWordBack)
                            uiWordCard?.let {
                                if (isDownloaded) {
                                    turkGermanTranslator.translate(uiWordCard.wordName)
                                        .addOnSuccessListener { traslatedString ->
                                            binding.textWordBack.text = traslatedString

                                        }
                                        .addOnFailureListener { exception ->
                                        }
                                } else {
                                    Toast.makeText(requireContext(), "model is not downloaded", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            front_anim.start()
                            back_anim.start()
                            isFront = false
                        } else {
                            front_anim.setTarget(binding.cardWordBack)
                            back_anim.setTarget(binding.cardWordFront)
                            front_anim.start()
                            back_anim.start()
                            isFront = true
                        }
                    }
                }
            }
        }
    }

    private fun getWordDetailData(item: UIWordCard) = with(binding) {
        textWordFront.text = item.wordName
    }

}