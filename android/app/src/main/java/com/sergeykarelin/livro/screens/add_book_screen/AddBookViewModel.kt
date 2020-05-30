package com.sergeykarelin.livro.screens.add_book_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.data.BundledBook
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.LibraryApi
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddBookViewModel : ViewModel() {

    private val service = RetrofitService.createGenericService(LibraryApi::class.java)

    val downloadBookResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadBundledBook(bundledBook: BundledBook) {
        if (!Utils.isOnline()) {
            downloadBookResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) {
                    service.addBundledBook(PreferencesUtils.getToken(), bundledBook.publicId)
                }

                if (response.isSuccessful) {
                    downloadBookResult.value = Result.SUCCESS
                } else {
                    downloadBookResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

}