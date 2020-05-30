package com.sergeykarelin.livro.screens.reader_screen

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.Constants.EMPTY_STRING
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.data.database.BookWithPages
import com.sergeykarelin.livro.data.database.Page
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.DictionaryApi
import com.sergeykarelin.livro.services.utils.BooksUtils
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReaderViewModel : ViewModel() {

    private val dictionaryService = RetrofitService.createScalarsService(DictionaryApi::class.java)

    private val booksDao by lazy { LivroApplication.database.booksDao() }

    private var bookId: String = EMPTY_STRING
    private var currentPage: Int = 0

    private lateinit var book: BookWithPages
    private lateinit var pages: List<Page>

    val titleLoadResult: MutableLiveData<String> = MutableLiveData()
    val bookLoadResult: MutableLiveData<String> = MutableLiveData()
    val wordSearchResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun loadBook(bookId: String, textView: TextView) {
        this.bookId = bookId

        viewModelScope.launch(Dispatchers.Main) {
            isProgressShowing.value = true

            withContext(Dispatchers.Default) {
                pages = getPages(textView)
            }

            currentPage = book.book.currentPage

            bookLoadResult.value = pages[currentPage].page
            titleLoadResult.value = book.book.title
            isProgressShowing.value = false
        }
    }

    fun loadNextPage() {
        viewModelScope.launch(Dispatchers.Main) {
            if (currentPage < pages.size) {
                currentPage++
                bookLoadResult.value = pages[currentPage].page
            }
        }
    }

    fun loadPreviousPage() {
        viewModelScope.launch(Dispatchers.Main) {
            if (currentPage > 0) {
                currentPage--

                bookLoadResult.value = pages[currentPage].page
            }
        }
    }

    fun searchForWord(word: String) {
        if (!Utils.isOnline()) {
            wordSearchResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) { dictionaryService.translateText(PreferencesUtils.getToken(), word) }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    wordSearchResult.value = Result.SUCCESS.apply { value = Pair(word, body) }
                } else {
                    wordSearchResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

    fun saveLastPage() {
        viewModelScope.launch(Dispatchers.Default) {
            booksDao.updatePage(book.book.id, currentPage)
        }
    }

    private suspend fun getPages(textView: TextView): List<Page> {
        book = booksDao.getBookById(bookId)
        val pages = book.pages
        return if (pages.isNotEmpty()) {
            pages
        } else {
            val splitPages = BooksUtils.splitBookInPages(book, textView)
            booksDao.addAllPages(splitPages)
            getPages(textView)
        }
    }


}