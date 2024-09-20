package com.eniskaner.eyojinteractivewordgames.translator.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojinteractivewordgames.databinding.ItemLanguageCodeSingleChooseBinding
import com.eniskaner.eyojinteractivewordgames.translator.data.model.LanguageModel
import com.eniskaner.eyojinteractivewordgames.translator.presentation.adapter.LanguageClickListener

class LanguageCodeViewHolder(
    private val binding: ItemLanguageCodeSingleChooseBinding,
    private val languageClickListener: LanguageClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bindLanguageCodesToRecyclerView(item: LanguageModel) = with(binding) {
        textViewLanguageCodeSingleChoose.text = item.languageName
        checkBoxLanguageCodeSingleChoose.setOnClickListener {
            languageClickListener.languageCodeClickListener(item = item, position = adapterPosition)
        }
    }
}