package com.sergeykarelin.livro.services.rest

import com.sergeykarelin.livro.Constants.EMPTY_STRING

data class GenericError(val code: String = EMPTY_STRING,
                        val message: String = EMPTY_STRING,
                        val httpCode: Int = 0) {

    companion object {

        val DEFAULT_ERROR = GenericError()

    }

}