package com.sergeykarelin.livro.screens.send_email_screen

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.services.extensions.getString
import com.sergeykarelin.livro.services.extensions.isValid
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import com.sergeykarelin.livro.services.utils.PreferencesUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.android.synthetic.main.activity_send_email.*

class SendEmailActivity : BaseActivity(R.layout.activity_send_email) {

    private val viewModel by lazy { ViewModelProvider(this).get(SendEmailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initClickListeners()
        initObservers()
    }

    private fun initToolbar() {
        setSupportActionBar(sendEmailToolbar)
        sendEmailToolbar.setNavigationOnClickListener { finish() }
    }

    private fun initClickListeners() {
        sendEmailNavigationSend.setOnClickListener {
            if (sendEmail.isValid(getString(R.string.send_email))) {
                viewModel.sendEmail(sendEmail.getString())
            }
        }
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        viewModel.sendEmailResult.observe(this, Observer { result ->
            if (result == Result.SUCCESS) {
                PreferencesUtils.lockApplication()
                DialogUtils.showLockedDialog(this)
            } else {
                DialogUtils.showServerErrorDialog(this, result.errorCode)
            }
        })
    }

}