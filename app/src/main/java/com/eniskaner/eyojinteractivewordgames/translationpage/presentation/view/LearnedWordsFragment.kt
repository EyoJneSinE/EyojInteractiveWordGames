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
import com.eniskaner.eyojinteractivewordgames.common.util.addCarouselEffect
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLearnedWordsBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.LearnedWordsCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearnedWordsFragment : BaseFragment<FragmentLearnedWordsBinding>(), CarouselClickListener {

    private val adapter by lazy { LearnedWordsCarouselAdapter(this@LearnedWordsFragment) }
    private val navController: NavController by lazy { findNavController() }
    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()

    override fun setBinding(): FragmentLearnedWordsBinding =
        FragmentLearnedWordsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        getLeanedEnglishWords()
        binding.swipeRefreshLayoutLearnedWords.setOnRefreshListener {
            getWordListData()
            binding.swipeRefreshLayoutLearnedWords.isRefreshing = false
        }
        getWordListData()
    }

    private fun initViewPager() {
        binding.viewPagerLearnedWords.addCarouselEffect()
        binding.viewPagerLearnedWords.adapter = adapter
    }

    private fun getLeanedEnglishWords() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getLearnedEnglishWords()
            }
        }
    }

    private fun getWordListData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.wordCardListState.collect { wordCardState ->
                    adapter.submitList(wordCardState.learnedEnglishCardList.filter { it.isEnglishLearned })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLeanedEnglishWords()
    }

    override fun wordCardClickListener(item: UIWordCard) {
        navigateToLearnedWordDetails(item = item)
    }

    private fun navigateToLearnedWordDetails(item: UIWordCard) {
        val bundle = bundleOf(
            "uiWordCard" to item
        )
        navController.navigate(R.id.action_learnedWordsFragment_to_learnedWordDetailsFragment, bundle)
    }

}