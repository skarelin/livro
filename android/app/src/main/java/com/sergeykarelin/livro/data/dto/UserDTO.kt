package com.sergeykarelin.livro.data.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String
)