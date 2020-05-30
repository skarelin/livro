package com.sergeykarelin.livro.screens.signup_screen

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.screens.books_screen.BooksActivity
import com.sergeykarelin.livro.screens.login_screen.LoginActivity
import com.sergeykarelin.livro.screens.login_screen.LoginViewModel
import com.sergeykarelin.livro.services.extensions.isValid
import com.sergeykarelin.livro.services.extensions.startActivity
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignupActivity : BaseActivity(R.layout.activity_sign_up) {

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initClickListeners()
        initObservers()
    }

    private fun initToolbar() {
        setSupportActionBar(signUpToolbar)
        signUpToolbar.setNavigationOnClickListener {
            startActivity(LoginActivity::class)
        }
    }

    private fun initClickListeners() {
        signUpNavigationButton.setOnClickListener {
            if (signUpUsername.isValid(getString(R.string.sign_up_username)) && signUpPassword.isValid(getString(R.string.sign_up_password))) {
                viewModel.performSignUp(signUpUsername.text.toString(), signUpPassword.text.toString())
            }
        }
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer { isShowing ->
            if (isShowing) showProgress() else hideProgress()
        })

        viewModel.signupResult.observe(this, Observer { result ->
            when (result) {
                Result.ERROR -> {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
                else -> {
                }
            }
        })

        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                Result.SUCCESS -> {
                    startActivity(Intent(applicationContext, BooksActivity::class.java))
                    finish()
                }
                else -> {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
            }
        })
    }

}