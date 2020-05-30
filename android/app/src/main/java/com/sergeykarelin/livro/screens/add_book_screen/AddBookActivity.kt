package com.sergeykarelin.livro.screens.add_book_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergeykarelin.livro.Constants.Keys.KEY_ADD_BOOK_FILE
import com.sergeykarelin.livro.Constants.Keys.KEY_ADD_BOOK_FINISHED
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.data.BundledBook
import com.sergeykarelin.livro.screens.add_book_screen.adapters.BundledBooksAdapter
import com.sergeykarelin.livro.screens.add_book_screen.upload_book_dialog.UploadBookDialog
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : BaseActivity(R.layout.activity_add_book) {

    private lateinit var uploadBookDialog: UploadBookDialog
    private val viewModel by lazy { ViewModelProvider(this).get(AddBookViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initBundledBooksList()
        initObservers()
        initClickListeners()
    }

    private fun initToolbar() {
        setSupportActionBar(addBookToolbar)
        addBookToolbar.setNavigationOnClickListener { finish() }
    }

    private fun initBundledBooksList() {
        addBookBundledList.apply {
            layoutManager = LinearLayoutManager(this@AddBookActivity)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            adapter = BundledBooksAdapter(BundledBook.buildList()) {
                viewModel.downloadBundledBook(it)
            }
        }
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(this, Observer { isShowing ->
            if (isShowing) showProgress() else hideProgress()
        })

        viewModel.downloadBookResult.observe(this, Observer { result ->
            when (result) {
                Result.SUCCESS -> {
                    Utils.showToast(R.string.add_book_status_downloaded)

                    val intent = Intent()
                    intent.putExtra(KEY_ADD_BOOK_FINISHED.toString(), true)
                    setResult(Activity.RESULT_OK, intent)
                }
                else -> {
                    DialogUtils.showServerErrorDialog(this, result.errorCode)
                }
            }
        })
    }

    private fun initClickListeners() {
        addBookUploadOwn.setOnClickListener {
            uploadBookDialog = UploadBookDialog(this) {
                val intent = Intent()
                intent.putExtra(KEY_ADD_BOOK_FINISHED.toString(), true)
                setResult(Activity.RESULT_OK, intent)
            }
            uploadBookDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode == KEY_ADD_BOOK_FILE && data != null && resultCode == Activity.RESULT_OK) {
                if (this::uploadBookDialog.isInitialized) {
                    uploadBookDialog.handleFileSelectionResult(data.data)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}