package com.sergeykarelin.livro

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.room.Room
import com.sergeykarelin.livro.database.BooksDatabase
import com.sergeykarelin.livro.screens.login_screen.LoginActivity
import com.sergeykarelin.livro.services.rest.Session
import com.sergeykarelin.livro.services.utils.PreferencesUtils

class LivroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this.applicationContext
        database = Room.databaseBuilder(
                        applicationContext,
                        BooksDatabase::class.java, BooksDatabase.NAME
                )
                .fallbackToDestructiveMigration().build()

        initializeSession()
    }

    private fun initializeSession(): Session {
        session = object : Session {

            override fun saveToken(token: String) = PreferencesUtils.saveToken(token)

            override fun getToken() = PreferencesUtils.getToken()

            override fun invalidate() {
                PreferencesUtils.removeToken()

                val intent = Intent(this@LivroApplication, LoginActivity::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        return session
    }

    companion object {
        lateinit var database: BooksDatabase private set
        lateinit var context: Context private set
        lateinit var session: Session private set
    }
}