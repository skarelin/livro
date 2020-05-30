package com.sergeykarelin.livro.screens.dictionary_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.DictionaryApi
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DictionaryViewModel : ViewModel() {

    private val service = RetrofitService.createGenericService(DictionaryApi::class.java)

    val wordsLoadResult: MutableLiveData<Result> = MutableLiveData()
    val wordDeleteResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun loadTranslations() {
        if (!Utils.isOnline()) {
            wordsLoadResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) { service.getTranslations(PreferencesUtils.getToken()) }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    wordsLoadResult.value = Result.SUCCESS.apply { value = body }
                } else {
                    wordsLoadResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

    fun deleteWord(word: String) {
        if (!Utils.isOnline()) {
            wordDeleteResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val response = withContext(Dispatchers.IO) { service.deleteWord(PreferencesUtils.getToken(), word) }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    wordDeleteResult.value = Result.SUCCESS.apply { value = word }
                } else {
                    wordDeleteResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }
}