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
import com.eniskaner.eyojinteractivewordgames.common.util.viewBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLearnedWordsBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordDetailBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.LearnedWordsCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.LearnedViewModel
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearnedWordsFragment : Fragment(R.layout.fragment_learned_words), CarouselClickListener {

    private val binding: FragmentLearnedWordsBinding by viewBinding(FragmentLearnedWordsBinding::bind)
    private val adapter by lazy { LearnedWordsCarouselAdapter(this@LearnedWordsFragment) }
    private val navController: NavController by lazy { findNavController() }
    private val learnedViewModel: LearnedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLearnedEnglishWord()
        initViewPager()
        getShuffleLearnedEnglishWords()
        getWordListData()
    }

    private fun initViewPager() = with(binding) {
        viewPagerLearnedWords.addCarouselEffect()
        viewPagerLearnedWords.adapter = adapter
    }

    private fun getShuffleLearnedEnglishWords() = with(binding) {
        swipeRefreshLayoutLearnedWords.setOnRefreshListener {
            learnedViewModel.shuffleEnglishWords()
            swipeRefreshLayoutLearnedWords.isRefreshing = false
        }
    }

    private fun getLearnedEnglishWord() {
        launchAndRepeatWithViewLifecycle {
            launch {
                learnedViewModel.getLearnedEnglishWords()
            }
        }
    }

    private fun getWordListData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                learnedViewModel.wordCardListState.collect { wordCardState ->
                    adapter.submitList(wordCardState.learnedEnglishCardList)
                }
            }
        }
    }

    override fun wordCardClickListener(item: UIWordCard) {
        navigateToLearnedWordDetails(item = item)
    }

    private fun navigateToLearnedWordDetails(item: UIWordCard) {
        val bundle = bundleOf(
            "uiWordCard" to item
        )
        navController.navigate(
            R.id.action_learnedWordsFragment_to_learnedWordDetailsFragment,
            bundle
        )
    }

}