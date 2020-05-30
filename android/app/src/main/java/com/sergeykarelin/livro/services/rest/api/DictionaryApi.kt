package com.sergeykarelin.livro.services.rest.api

import com.sergeykarelin.livro.data.dto.TranslationDTO
import retrofit2.Response
import retrofit2.http.*

interface DictionaryApi {

    @GET("yandex/translate/{text}")
    suspend fun translateText(@Header("Authorization") bearer: String, @Path("text") text: String): Response<String>

    @GET("yandex/words")
    suspend fun getTranslations(@Header("Authorization") bearer: String): Response<TranslationDTO>

    @DELETE("yandex/dictionary/delete/{text}")
    suspend fun deleteWord(@Header("Authorization") bearer: String, @Path("text") text: String): Response<Unit>

}