package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.base.BaseFragment
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.PrefUtils
import com.eniskaner.eyojinteractivewordgames.common.util.addCarouselEffect
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordListBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCardProvider
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : BaseFragment<FragmentWordListBinding>(), CarouselClickListener {

    private val adapter by lazy { WordCarouselAdapter(this@WordListFragment) }
    private val navController: NavController by lazy { findNavController() }
    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()
    private var isEnglishDownloaded: Boolean = false
    private var isGermanDownloaded: Boolean = false

    @Inject
    lateinit var uiWordCardProvider: UIWordCardProvider

    @Inject
    lateinit var prefUtils: PrefUtils
    override fun setBinding(): FragmentWordListBinding =
        FragmentWordListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!prefUtils.isSavedList()) {
            sharedWordCardViewModel.saveWordCards()
            prefUtils.savedList(true)
        }

        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getWordCards()
            }
        }

        initViewPager()
        getLearnableWords()
        binding.swipeRefreshLayoutWordList.setOnRefreshListener {
            sharedWordCardViewModel.shuffleWords()
            binding.swipeRefreshLayoutWordList.isRefreshing = false
        }
        getWordListData()
    }

    private fun initViewPager() {
        binding.viewPagerWordList.addCarouselEffect()
        binding.viewPagerWordList.adapter = adapter
    }

    private fun getLearnableWords() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getLearnableWords()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLearnableWords()
    }

    private fun getWordListData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.wordCardListState.collect { wordCardState ->
                    adapter.submitList(wordCardState.wordCardsList.filter { !it.isEnglishLearned or !it.isGermanLearned })
                }
            }
        }
    }

    override fun wordCardClickListener(item: UIWordCard) {
        navigateToWordDetails(item)
    }

    private fun navigateToWordDetails(item: UIWordCard) {
        val bundle = bundleOf(
            "uiWordCard" to item
        )
        navController.navigate(R.id.action_wordListFragment_to_wordDetailFragment, bundle)
    }
}