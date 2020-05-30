package com.sergeykarelin.livro.services.rest.interceptors

import com.sergeykarelin.livro.services.rest.Session
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val session: Session) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 401) {
            session.invalidate()
        }

        return response
    }
}