package com.sergeykarelin.livro.screens.books_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergeykarelin.livro.Constants.Keys.KEY_ADD_BOOK_FINISHED
import com.sergeykarelin.livro.Constants.Keys.KEY_READER_SELECTED_BOOK
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.data.dto.BookDTO
import com.sergeykarelin.livro.screens.add_book_screen.AddBookActivity
import com.sergeykarelin.livro.screens.books_screen.adapters.BooksAdapter
import com.sergeykarelin.livro.screens.books_screen.listeners.BooksEventsListener
import com.sergeykarelin.livro.screens.dictionary_screen.DictionaryActivity
import com.sergeykarelin.livro.screens.login_screen.LoginActivity
import com.sergeykarelin.livro.screens.reader_screen.ReaderActivity
import com.sergeykarelin.livro.services.extensions.startActivity
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : BaseActivity(R.layout.activity_books), BooksEventsListener {

    private val viewModel by lazy { ViewModelProvider(this).get(BooksViewModel::class.java) }

    private lateinit var adapter: BooksAdapter

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleCurrentState()

        initRefreshListener()
        initToolbar()
        initClickListeners()
        initObservers()
    }

    private fun handleCurrentState() {
        when {
            PreferencesUtils.isApplicationLocked() -> DialogUtils.showLockedDialog(this)

            PreferencesUtils.isTokenExists() -> {
                if (!PreferencesUtils.wasGuideShown()) {
                    DialogUtils.showGuideDialog(this, viewModel)
                } else {
                    viewModel.getBooks()
                }
            }

            else -> {
                startActivity(LoginActivity::class)
                finish()
            }
        }

    }

    private fun initRefreshListener() {
        booksNavigationRefresh.setOnRefreshListener {
            viewModel.getBooks()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(booksToolbar)
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer { isShowing ->
            if (isShowing) showProgress() else hideProgress()
        })

        viewModel.isLoadingBooksDialogShowing.observe(this, Observer { isShowing ->
            if (isShowing) showLoadingBooksDialog() else hideLoadingBooksDialog()
        })

        viewModel.loadBooksResult.observe(this, Observer { result ->
            when (result) {
                Result.SUCCESS -> {
                    initList(result.value as List<BookDTO>)
                }
                else -> {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
            }
        })

        viewModel.deleteBookResult.observe(this, Observer { result ->
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

    private fun initList(books: List<BookDTO>) {
        if (booksNavigationRefresh.isRefreshing) booksNavigationRefresh.isRefreshing = false

        adapter = BooksAdapter(books.toMutableList(), this)

        booksList.apply {
            layoutManager = LinearLayoutManager(this@BooksActivity)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            adapter = this@BooksActivity.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val topRowVerticalPosition = if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                    booksNavigationRefresh.isEnabled = topRowVerticalPosition >= 0
                }
            })
        }
    }

    private fun initClickListeners() {
        booksNavigationAddBook.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivityForResult(intent, KEY_ADD_BOOK_FINISHED)
        }

        booksNavigationDictionary.setOnClickListener {
            startActivity(Intent(this, DictionaryActivity::class.java))
        }
    }

    override fun deleteBook(book: BookDTO) {
        viewModel.deleteBook(book.id)
    }

    override fun openBook(book: BookDTO) {
        val intent = Intent(this, ReaderActivity::class.java)
        intent.putExtra(KEY_READER_SELECTED_BOOK, book.id)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.helper_exit_hint, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KEY_ADD_BOOK_FINISHED && resultCode == Activity.RESULT_OK) {
            viewModel.getBooks()
        }
    }
}