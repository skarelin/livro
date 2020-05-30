package com.sergeykarelin.livro.services.rest.api

import com.sergeykarelin.livro.data.dto.EmailDTO
import com.sergeykarelin.livro.data.dto.UserDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UsersApi {

    @POST("users/login/")
    suspend fun performLogin(@Body userDTO: UserDTO): Response<String>

    @POST("users/register/")
    suspend fun performRegistration(@Body userDTO: UserDTO): Response<Unit>

    @POST("users/sendEmail/")
    suspend fun sendEmail(@Header("Authorization") bearer: String, @Body emailDTO: EmailDTO): Response<Unit>

    @GET("health-check/status")
    fun checkServerHealthStatus(): Call<Unit>
}