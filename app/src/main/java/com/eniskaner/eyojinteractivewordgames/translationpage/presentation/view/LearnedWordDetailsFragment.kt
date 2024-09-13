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

@AndroidEntryPoint
class LearnedWordDetailsFragment : BaseFragment<FragmentLearnedWordDetailsBinding>() {

    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private lateinit var back_anim: AnimatorSet
    private lateinit var front_anim: AnimatorSet
    var isFront = true
    var  isDownloaded:Boolean = false
    var isEnglish: Boolean = true
    override fun setBinding(): FragmentLearnedWordDetailsBinding =
        FragmentLearnedWordDetailsBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    getLearnedWordDetailData(it)
                    binding.switchIsEnglishLearned.isChecked = uiWordCard.isEnglishLearned
                    binding.switchIsEnglishLearned.setOnCheckedChangeListener { _, isChecked ->
                        sharedWordCardViewModel.updateWordCard(updatedCard = uiWordCard, isEnglishLearned = isChecked, isGermanLearned = false)
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

    private fun getLearnedWordDetailData(item: UIWordCard) = with(binding) {
        textWordFront.text = item.wordName
    }

}