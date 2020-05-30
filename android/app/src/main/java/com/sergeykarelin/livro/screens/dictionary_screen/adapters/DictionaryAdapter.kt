package com.sergeykarelin.livro.screens.dictionary_screen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.data.dto.TranslationDTO
import com.sergeykarelin.livro.screens.dictionary_screen.listeners.DictionaryListListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dictionary_word.*

class DictionaryAdapter(private val data: MutableList<TranslationDTO.Translation>, private val listener: DictionaryListListener) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    fun removeItem(original: String) {
        val position = data.indexOf(data.find { it.original == original })

        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary_word, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(override val containerView: View, private val listener: DictionaryListListener) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(translation: TranslationDTO.Translation) {
            dictionaryTextOriginal.text = translation.original
            dictionaryTextTranslation.text = translation.translation

            dictionaryDelete.setOnClickListener { listener.deleteWord(translation.original) }
            containerView.setOnClickListener { listener.findWord(translation) }
        }

    }
}