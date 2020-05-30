package com.sergeykarelin.livro.screens.reader_screen

import android.os.Bundle
import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergeykarelin.livro.Constants.EMPTY_STRING
import com.sergeykarelin.livro.Constants.Errors.ERROR_CODE_106
import com.sergeykarelin.livro.Constants.Keys.KEY_READER_SELECTED_BOOK
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.screens.send_email_screen.SendEmailActivity
import com.sergeykarelin.livro.services.extensions.prepareForServer
import com.sergeykarelin.livro.services.extensions.startActivity
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import com.sergeykarelin.livro.widgets.ReaderMovementMethod
import kotlinx.android.synthetic.main.activity_reader.*

class ReaderActivity : BaseActivity(R.layout.activity_reader) {

    private val viewModel by lazy { ViewModelProvider(this).get(ReaderViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readerTextLayout.doOnLayout {
            viewModel.loadBook(intent.extras?.getString(KEY_READER_SELECTED_BOOK)
                    ?: EMPTY_STRING, readerText)
        }

        initReader()
        initObservers()
        initClickListeners()
    }

    private fun initReader() {
        ReaderMovementMethod().apply {
            setOnTextClickListener(object : ReaderMovementMethod.OnTextClickListener {
                override fun onClick(text: String): Boolean {
                    viewModel.searchForWord(text.prepareForServer())
                    return true
                }

                override fun onLongClick(text: String): Boolean {
                    viewModel.searchForWord(text.prepareForServer())
                    return true
                }
            })

            readerText.movementMethod = this
        }
    }

    private fun initObservers() {
        viewModel.bookLoadResult.observe(this, Observer {
            readerText.text = it.trim()
        })

        viewModel.titleLoadResult.observe(this, Observer {
            initToolbar(it)
        })

        viewModel.wordSearchResult.observe(this, Observer { result ->
            if (result == Result.SUCCESS) {
                DialogUtils.showReaderTextTranslationDialog(this, result.value as Pair<String, String>)
            } else {
                if (result.errorCode.code == ERROR_CODE_106) {
                    startActivity(SendEmailActivity::class)
                } else {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
            }
        })

        viewModel.isProgressShowing.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })
    }

    private fun initToolbar(title: String) {
        setSupportActionBar(readerToolbar)

        readerToolbar.title = title
        readerToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initClickListeners() {
        readerNavigationNextPage.setOnClickListener {
            viewModel.loadNextPage()
        }

        readerNavigationPreviousPage.setOnClickListener {
            viewModel.loadPreviousPage()
        }
    }

    override fun onStop() {
        viewModel.saveLastPage()
        super.onStop()
    }
}