package com.sergeykarelin.livro.services.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.sergeykarelin.livro.Constants.EMPTY_STRING
import com.sergeykarelin.livro.LivroApplication

object PreferencesUtils {

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LivroApplication.context) as SharedPreferences

    fun isTokenExists() = sharedPreferences.contains(KEY_BEARER)

    fun getToken(): String {
        val bearer = sharedPreferences.getString(KEY_BEARER, EMPTY_STRING)
        return if (bearer != null) {
            "Bearer $bearer"
        } else {
            EMPTY_STRING
        }
    }

    fun saveToken(bearer: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_BEARER, bearer)
            apply()
        }
    }

    fun removeToken(): Any = sharedPreferences.edit().remove(KEY_BEARER)

    fun lockApplication() = with(sharedPreferences.edit()) {
        putBoolean(KEY_LOCK, true)
        apply()
    }

    fun isApplicationLocked() = sharedPreferences.contains(KEY_LOCK)

    fun finishShowingGuide() {
        with(sharedPreferences.edit()) {
            putBoolean(GUIDE_SHOWN, true)
            apply()
        }
    }

    fun wasGuideShown() = sharedPreferences.contains(GUIDE_SHOWN)

    private const val KEY_BEARER = "KEY_BEAERER"
    private const val KEY_LOCK = "KEY_LOCK"
    private const val GUIDE_SHOWN = "GUIDE_SHOWN"

}