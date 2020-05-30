package com.sergeykarelin.livro.services.rest

import com.google.gson.GsonBuilder
import com.sergeykarelin.livro.Constants
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.services.rest.interceptors.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitService {

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val gson = GsonBuilder()
            .setLenient()
            .create()

    private val genericClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthorizationInterceptor(LivroApplication.session))
            .build()

    private val loginClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    private val genericRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(genericClient)
            .build()

    private val scalarsRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(genericClient)
            .build()

    private val loginRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(loginClient)
            .build()

    fun <S> createGenericService(serviceClass: Class<S>): S {
        return genericRetrofit.create(serviceClass)
    }

    fun <S> createLoginService(serviceClass: Class<S>): S {
        return loginRetrofit.create(serviceClass)
    }

    fun <S> createScalarsService(serviceClass: Class<S>): S {
        return scalarsRetrofit.create(serviceClass)
    }

}

enum class Result {
    SUCCESS, ERROR;

    lateinit var value: Any
    var errorCode: GenericError = GenericError.DEFAULT_ERROR
}