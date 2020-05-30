package com.sergeykarelin.livro.screens.add_book_screen.upload_book_dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.LibraryApi
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadBookViewModel : ViewModel() {

    private val service = RetrofitService.createGenericService(LibraryApi::class.java)

    val addBookResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun addNewBook(file: File, fileName: String, title: String) {
        if (!Utils.isOnline()) {
            addBookResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) {
                    val newBookBody = MultipartBody.Part.createFormData("file", fileName, file.asRequestBody(MultipartBody.FORM))
                    service.addNewBook(PreferencesUtils.getToken(), newBookBody, title)
                }

                if (response.isSuccessful) {
                    addBookResult.value = Result.SUCCESS
                } else {
                    addBookResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

}