package com.eniskaner.eyojinteractivewordgames.translator.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eniskaner.eyojinteractivewordgames.R
import com.eniskaner.eyojinteractivewordgames.common.util.launchAndRepeatWithViewLifecycle
import com.eniskaner.eyojinteractivewordgames.common.util.viewBinding
import com.eniskaner.eyojinteractivewordgames.databinding.FragmentLanguageCodeBinding
import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel
import com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter.LanguageClickListener
import com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter.LanguageCodeAdapter
import com.eniskaner.eyojinteractivewordgames.translator.presentation.viewmodel.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageCodeFragment : Fragment(), LanguageClickListener {

    private val binding by viewBinding(FragmentLanguageCodeBinding::bind)
    private val viewModel: LanguageViewModel by viewModels()
    private val adapter by lazy { LanguageCodeAdapter(this@LanguageCodeFragment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        getLanguageCodeData()
        observeLanguageCodeData()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerViewSingleChooseLanguageCode.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSingleChooseLanguageCode.adapter = adapter
    }

    private fun getLanguageCodeData() {
        viewModel.getLanguageCodes()
    }

    private fun observeLanguageCodeData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.languageCodeState.collect { languageCodeState ->
                    adapter.submitList(languageCodeState.languageCodes)
                }
            }
        }
    }

    override fun languageCodeClickListener(item: LanguageModel, position: Int) {

    }

}