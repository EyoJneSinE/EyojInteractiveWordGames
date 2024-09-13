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
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLearnedGermanWordsBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.LearnedGermanAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.viewmodel.SharedWordCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearnedGermanWordsFragment : BaseFragment<FragmentLearnedGermanWordsBinding>(), CarouselClickListener {

    private val adapter by lazy { LearnedGermanAdapter(this@LearnedGermanWordsFragment) }
    private val navController: NavController by lazy { findNavController() }
    private val sharedWordCardViewModel: SharedWordCardViewModel by viewModels()

    override fun setBinding(): FragmentLearnedGermanWordsBinding =
        FragmentLearnedGermanWordsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        getLeanedGermanWords()
        binding.swipeRefreshLayoutLearnedGermanWords.setOnRefreshListener {
            getGermanWordListData()
            binding.swipeRefreshLayoutLearnedGermanWords.isRefreshing = false
        }
        getGermanWordListData()
    }

    private fun initViewPager() {
        binding.viewPagerLearnedGermanWords.addCarouselEffect()
        binding.viewPagerLearnedGermanWords.adapter = adapter
    }

    private fun getLeanedGermanWords() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.getLearnedGermanWords()
            }
        }
    }

    private fun getGermanWordListData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                sharedWordCardViewModel.wordCardListState.collect { wordCardState ->
                    adapter.submitList(wordCardState.learnedEnglishCardList.filter { it.isGermanLearned })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLeanedGermanWords()
    }


    override fun wordCardClickListener(item: UIWordCard) {
        navigateToLearnedGermanWordDetails(item = item)
    }

    private fun navigateToLearnedGermanWordDetails(item: UIWordCard) {
        val bundle = bundleOf(
            "uiWordCard" to item
        )
        navController.navigate(R.id.action_learnedGermanWordsFragment_to_learnedGermanWordDetailFragment, bundle)
    }

}