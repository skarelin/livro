package com.sergeykarelin.livro.screens.send_email_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.data.dto.EmailDTO
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.UsersApi
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendEmailViewModel : ViewModel() {

    private val service = RetrofitService.createGenericService(UsersApi::class.java)

    val sendEmailResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun sendEmail(email: String) {
        if (!Utils.isOnline()) {
            sendEmailResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true
                val response = withContext(Dispatchers.IO) { service.sendEmail(PreferencesUtils.getToken(), EmailDTO(email)) }

                if (response.isSuccessful) {
                    sendEmailResult.value = Result.SUCCESS
                } else {
                    sendEmailResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

}