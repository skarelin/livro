package com.sergeykarelin.livro.screens.login_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.data.dto.UserDTO
import com.sergeykarelin.livro.services.extensions.toGenericError
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.rest.RetrofitService
import com.sergeykarelin.livro.services.rest.api.UsersApi
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val service = RetrofitService.createLoginService(UsersApi::class.java)

    val loginResult: MutableLiveData<Result> = MutableLiveData()
    val signupResult: MutableLiveData<Result> = MutableLiveData()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()
    val isTechWorkDialogShowing: MutableLiveData<Boolean> = MutableLiveData()

    fun checkServerConnection() {

        var serverStatus = service.checkServerHealthStatus();

        serverStatus.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>?, t: Throwable?) {
                println("Cannot send request to the server.")
            }

            override fun onResponse(call: Call<Unit>?, response: Response<Unit>?) {
                if (response != null) {
                    if(response.code() == 200) {
                        println("Init status from server: OK")
                    } else {
                        isTechWorkDialogShowing.value = true
                        println("Something went wrong during the connection to server: " + response.code())
                    }
                } else {
                    println("Response is null")
                }
            }
        })
    }

    fun performLogin(login: String, password: String, afterRegistration: Boolean = false) {
        if (!Utils.isOnline()) {
            loginResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {

                isProgressShowing.value = true

                val userLoginDTO = UserDTO(login, password)
                val response = withContext(Dispatchers.IO) { service.performLogin(userLoginDTO) }

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    LivroApplication.session.saveToken(body)

                    loginResult.value = Result.SUCCESS

                    if (afterRegistration) {
                        signupResult.value = Result.SUCCESS
                    }
                } else {
                    loginResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }

                isProgressShowing.value = false
            }
        }
    }

    fun performSignUp(login: String, password: String) {
        if (!Utils.isOnline()) {
            signupResult.value = Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val userLoginDTO = UserDTO(login, password)
                val response = withContext(Dispatchers.IO) { service.performRegistration(userLoginDTO) }

                if (response.isSuccessful) {
                    performLogin(login, password, true)
                    signupResult.value = Result.SUCCESS
                } else {
                    isProgressShowing.value = false
                    signupResult.value = Result.ERROR.apply { errorCode = response.errorBody().toGenericError() }
                }
            }
        }
    }

}