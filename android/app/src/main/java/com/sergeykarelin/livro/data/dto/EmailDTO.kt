package com.sergeykarelin.livro.data.dto

import com.google.gson.annotations.SerializedName

data class EmailDTO(
        @SerializedName("email")
        val email: String
)