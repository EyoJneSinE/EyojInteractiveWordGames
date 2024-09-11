package com.eniskaner.eyojinteractivewordgames.translationpage.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.base.BaseFragment
import com.eniskaner.eyojinteractivewordgames.common.util.addCarouselEffect
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentWordListBinding
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCard
import com.eniskaner.eyojinteractivewordgames.translationpage.data.model.UIWordCardProvider
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.CarouselClickListener
import com.eniskaner.eyojinteractivewordgames.translationpage.presentation.adapter.WordCarouselAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : BaseFragment<FragmentWordListBinding>(), CarouselClickListener {

    private val adapter by lazy { WordCarouselAdapter(this@WordListFragment) }
    private val navController: NavController by lazy { findNavController() }

    @Inject
    lateinit var uiWordCardProvider: UIWordCardProvider
    override fun setBinding(): FragmentWordListBinding =
        FragmentWordListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        binding.swipeRefreshLayoutWordList.setOnRefreshListener {
            initViewPager()
            binding.swipeRefreshLayoutWordList.isRefreshing = false
        }
    }

    private fun initViewPager() {
        binding.viewPager.addCarouselEffect()
        binding.viewPager.adapter = adapter
        adapter.submitList(uiWordCardProvider.addWords())
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