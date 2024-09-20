package com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.eniskaner.eyojinteractivewordgames.databinding.ItemLanguageCodeSingleChooseBinding
import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel
import com.eniskaner.eyojinteractivewordgames.translator.presentation.viewholder.LanguageCodeViewHolder

class LanguageCodeAdapter(
    private val languageClickListener: LanguageClickListener
) : ListAdapter<LanguageModel, LanguageCodeViewHolder>(LanguageCodeDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageCodeViewHolder {
        val binding =
            ItemLanguageCodeSingleChooseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LanguageCodeViewHolder(
            binding = binding,
            languageClickListener = languageClickListener
        )
    }

    override fun onBindViewHolder(holder: LanguageCodeViewHolder, position: Int) {
        holder.bindLanguageCodesToRecyclerView(getItem(position))
    }
}