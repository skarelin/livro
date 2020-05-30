package com.sergeykarelin.livro.screens.books_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.data.database.Book
import com.sergeykarelin.livro.data.dto.BookDTO
import com.sergeykarelin.livro.services.extensions.copyToFile
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.extensions.whenNotNull
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.LibraryApi
import com.sergeykarelin.livro.services.utils.BooksUtils
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class BooksViewModel : ViewModel() {

    private val service = RetrofitService.createGenericService(LibraryApi::class.java)

    val loadBooksResult: MutableLiveData<Result> = MutableLiveData()
    val deleteBookResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingBooksDialogShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun getBooks() {
        if (!Utils.isOnline()) {
            loadBooksResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isLoadingBooksDialogShowing.value = true

                val response = withContext(Dispatchers.IO) { service.getBooks(PreferencesUtils.getToken()) }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    fillUpDatabase(body)
                    loadBooksResult.value = Result.SUCCESS.apply { value = body }
                } else {
                    loadBooksResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }
            }
        }
    }

    fun deleteBook(id: String) {
        if (!Utils.isOnline()) {
            deleteBookResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) {
                    service.deleteBook(PreferencesUtils.getToken(), id)
                }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    deleteBookResult.value = Result.SUCCESS.apply { value = id }
                } else {
                    deleteBookResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

    private fun fillUpDatabase(books: List<BookDTO>) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadMissingBooks(books)

            val booksDao = LivroApplication.database.booksDao()
            booksDao.addAllBooks(books.map { Book(it.id, it.title, "") })
        }
    }

    private suspend fun downloadMissingBooks(books: List<BookDTO>) {
        books.forEach { bookDTO ->
            PreferencesUtils.getToken().whenNotNull {
                val response = service.downloadBook(it, bookDTO.id)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val file = File(BooksUtils.parentStorage.absolutePath + "/${bookDTO.id}.txt")
                    if (!file.exists()) {
                        file.copyToFile(responseBody.byteStream())
                    }
                }
            }
        }
        viewModelScope.launch(Dispatchers.Main) {isLoadingBooksDialogShowing.value = false}
    }

}