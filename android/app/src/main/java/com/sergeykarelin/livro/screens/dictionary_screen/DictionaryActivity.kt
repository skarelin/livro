package com.sergeykarelin.livro.screens.dictionary_screen

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.data.dto.TranslationDTO
import com.sergeykarelin.livro.screens.dictionary_screen.adapters.DictionaryAdapter
import com.sergeykarelin.livro.screens.dictionary_screen.listeners.DictionaryListListener
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : BaseActivity(R.layout.activity_dictionary), DictionaryListListener {

    private val viewModel by lazy { ViewModelProvider(this).get(DictionaryViewModel::class.java) }

    private lateinit var adapter: DictionaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initObservers()

        viewModel.loadTranslations()
    }

    override fun findWord(translation: TranslationDTO.Translation) {
        DialogUtils.showReaderTextTranslationDialog(this, Pair(translation.original, translation.translation))
    }

    override fun deleteWord(text: String) {
        viewModel.deleteWord(text)
    }

    private fun initToolbar() {
        setSupportActionBar(dictionaryToolbar)
        dictionaryToolbar.setNavigationOnClickListener { finish() }
    }

    private fun initList(translationDTO: TranslationDTO) {
        adapter = DictionaryAdapter(translationDTO.translations.reversed().toMutableList(), this)

        dictionaryList.apply {
            adapter = this@DictionaryActivity.adapter
            layoutManager = LinearLayoutManager(this@DictionaryActivity)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer { if (it) showProgress() else hideProgress() })

        viewModel.wordsLoadResult.observe(this, Observer { result ->
            if (result == Result.SUCCESS) {
                initList(result.value as TranslationDTO)
            } else {
                DialogUtils.showServerErrorDialog(this)
            }
        })

        viewModel.wordDeleteResult.observe(this, Observer { result ->
            when (result) {
                Result.SUCCESS -> {
                    if (this::adapter.isInitialized) {
                        adapter.removeItem(result.value as String)
                    }
                }
                else -> {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
            }
        })
    }

}