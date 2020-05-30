package com.sergeykarelin.livro.services.rest

interface Session {

    fun saveToken(token: String)

    fun getToken(): String

    fun invalidate()

}