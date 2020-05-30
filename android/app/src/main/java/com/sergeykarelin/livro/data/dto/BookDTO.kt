package com.sergeykarelin.livro.data.dto

import com.google.gson.annotations.SerializedName

data class BookDTO(
        @SerializedName("id")
        val id: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("filename")
        val filename: String,
        @SerializedName("fileExtension")
        val fileExtension: String,
        @SerializedName("librarySourceType")
        val librarySourceType: String
)