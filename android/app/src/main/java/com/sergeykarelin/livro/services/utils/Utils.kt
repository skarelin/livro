package com.sergeykarelin.livro.services.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.data.WordInfo

object Utils {

    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager = LivroApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(LivroApplication.context, text, duration).show()
    }

    fun showToast(textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(LivroApplication.context.getString(textRes), duration)
    }

}