package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.sharedpreferences.PrefUtils
import com.eniskaner.eyojinteractivewordgames.common.util.addCarouselEffect
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.common.util.viewBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordListBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCardProvider
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : Fragment(R.layout.fragment_word_list), CarouselClickListener {

    private val binding: FragmentWordListBinding by viewBinding(FragmentWordListBinding::bind)
    private val adapter by lazy { WordCarouselAdapter(this@WordListFragment) }
    private val navController: NavController by lazy { findNavController() }
    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()

    @Inject
    lateinit var uiWordCardProvider: UIWordCardProvider

    @Inject
    lateinit var prefUtils: PrefUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!prefUtils.isSavedList()) {
            sharedWordCardViewModel.saveWordCards()
            prefUtils.savedList(true)
        }

        initViewPager()
        getInitialWordList()
        getLearnableWords()
        swiperRefreshToWordList()
        getWordListData()
    }

    private fun initViewPager() {
        with(binding) {
            viewPagerWordList.addCarouselEffect()
            viewPagerWordList.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        getLearnableWords()
    }

    private fun getInitialWordList() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getWordCards()
            }
        }
    }

    private fun getLearnableWords() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getLearnableWords()
            }
        }
    }

    private fun swiperRefreshToWordList() {
        with(binding) {
            swipeRefreshLayoutWordList.setOnRefreshListener {
                sharedWordCardViewModel.shuffleWords()
                swipeRefreshLayoutWordList.isRefreshing = false
            }
        }
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