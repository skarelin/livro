package com.sergeykarelin.livro.services.rest.api

import com.sergeykarelin.livro.data.dto.BookDTO
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LibraryApi {

    @Multipart
    @POST("user-library/save/{title}")
    suspend fun addNewBook(@Header("Authorization") bearer: String,
                           @Part book: MultipartBody.Part,
                           @Path("title") title: String): Response<Unit>

    @POST("public-library/save/{publicId}")
    suspend fun addBundledBook(@Header("Authorization") bearer: String,
                               @Path("publicId") publicId: Int): Response<Unit>

    @GET("user-library/user/books/")
    suspend fun getBooks(@Header("Authorization") bearer: String): Response<List<BookDTO>>

    @DELETE("user-library/delete/{bookId}")
    suspend fun deleteBook(@Header("Authorization") bearer: String, @Path("bookId") bookId: String): Response<Unit>

    @GET("user-library/download/{bookId}")
    @Streaming
    suspend fun downloadBook(@Header("Authorization") bearer: String, @Path("bookId") bookId: String): Response<ResponseBody>

}