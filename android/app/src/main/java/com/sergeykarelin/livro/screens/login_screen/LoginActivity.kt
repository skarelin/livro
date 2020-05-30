package com.sergeykarelin.livro.screens.login_screen

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.screens.books_screen.BooksActivity
import com.sergeykarelin.livro.screens.signup_screen.SignupActivity
import com.sergeykarelin.livro.services.extensions.isValid
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(R.layout.activity_login) {

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClickListeners()
        initObservers()
        checkServerConnection();
    }

    private fun checkServerConnection() {
        viewModel.checkServerConnection();
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer { isShowing ->
            if (isShowing) showProgress() else hideProgress()
        })

        viewModel.isTechWorkDialogShowing.observe(this, Observer { isShowing ->
            if (isShowing) showTechWorkDialog() else hideTechWorkDialog()
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

    private fun initClickListeners() {
        loginNavigationLogin.setOnClickListener {
            if (loginUsername.isValid(getString(R.string.sign_up_username)) && loginPassword.isValid(getString(R.string.sign_up_password))) {
                viewModel.performLogin(loginUsername.text.toString(), loginPassword.text.toString())
            }
        }

        loginNavigationSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
            finish()
        }
    }
}