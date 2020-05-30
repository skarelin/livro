package com.sergeykarelin.livro.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.screens.login_screen.LoginActivity
import com.sergeykarelin.livro.screens.reader_screen.ReaderActivity
import com.sergeykarelin.livro.services.utils.DesignUtils
import com.sergeykarelin.livro.services.utils.DialogUtils

@SuppressLint("Registered")
abstract class BaseActivity(layoutRes: Int = 0) : AppCompatActivity(layoutRes) {

    private val progressDialog by lazy { DialogUtils.createProgressDialog(this) }
    private val loadingBooksDialog by lazy { DialogUtils.createLoadingBooksDialog(this) }
    private val techWorkDialog by lazy { DialogUtils.createTechWorkDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        correctAppearance()
        super.onCreate(savedInstanceState)
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    fun showProgress() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun showLoadingBooksDialog() {
        if (!loadingBooksDialog.isShowing) {
            loadingBooksDialog.show()
        }
    }

    fun hideLoadingBooksDialog() {
        if (loadingBooksDialog.isShowing) {
            loadingBooksDialog.dismiss()
        }
    }

    fun showTechWorkDialog() {
        if (!techWorkDialog.isShowing) {
            techWorkDialog.show()
        }
    }

    fun hideTechWorkDialog() {
        if (techWorkDialog.isShowing) {
            techWorkDialog.dismiss()
        }
    }


    private fun correctAppearance() {
        val view = window.decorView

        if (this is LoginActivity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DesignUtils.tintStatusBar(this, R.color.colorBackground)
            DesignUtils.setLightStatusBar(view)
        }

        if (this !is ReaderActivity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DesignUtils.tintNavigationBar(this, DesignUtils.getColor(this, R.color.colorBackground))
            DesignUtils.setLightNavigationBarIcons(view)
        } else {
            DesignUtils.tintNavigationBar(this, DesignUtils.getColor(this, R.color.colorBlack))
        }

    }

}