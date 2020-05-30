package com.sergeykarelin.livro.services.utils

import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergeykarelin.livro.Constants.Errors.ERROR_CODE_1
import com.sergeykarelin.livro.Constants.Errors.ERROR_CODE_102
import com.sergeykarelin.livro.Constants.Errors.ERROR_CODE_105
import com.sergeykarelin.livro.Constants.Errors.ERROR_CODE_203
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.screens.books_screen.BooksViewModel
import com.sergeykarelin.livro.services.rest.GenericError
import kotlinx.android.synthetic.main.dialog_bottom_guide.view.*
import kotlinx.android.synthetic.main.dialog_progress.view.*
import kotlinx.android.synthetic.main.dialog_translation.view.*

object DialogUtils {

    fun createProgressDialog(activity: BaseActivity): AlertDialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_progress, null)

        val builder = MaterialAlertDialogBuilder(activity)

        view.dialogProgressIndicator.isIndeterminate = true

        return builder.create().apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setView(view)
        }
    }

    fun createLoadingBooksDialog(activity: BaseActivity): AlertDialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_loading_books, null)

        val builder = MaterialAlertDialogBuilder(activity)

        view.dialogProgressIndicator.isIndeterminate = true

        return builder.create().apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setView(view)
        }
    }

    fun createTechWorkDialog(activity: BaseActivity): AlertDialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_tech_work, null)

        val builder = MaterialAlertDialogBuilder(activity)

        view.dialogProgressIndicator.isIndeterminate = true

        return builder.create().apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setView(view)
        }
    }

    fun showReaderTextTranslationDialog(activity: BaseActivity, translation: Pair<String, String>) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_translation, null)

        val builder = MaterialAlertDialogBuilder(activity).apply {
            setPositiveButton(R.string.dialog_translation_button_close) { dialog, _ -> dialog.dismiss() }
        }

        view.translationDialogTextOriginal.text = translation.first
        view.translationDialogTextLocalization.text = translation.second

        builder.create().apply {
            setView(view)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            show()
        }
    }

    fun showServerErrorDialog(activity: BaseActivity, errorBody: GenericError = GenericError.DEFAULT_ERROR) {
        val resolvedError = resolveServerError(errorBody)

        val builder = MaterialAlertDialogBuilder(activity).apply {
            setTitle(resolvedError.first)
            setMessage(resolvedError.second)
            setPositiveButton(R.string.dialog_server_error_button_close) { dialog, _ -> dialog.dismiss() }
        }

        builder.create().apply { show() }
    }

    fun showLockedDialog(activity: BaseActivity) {
        if (PreferencesUtils.isApplicationLocked()) {
            val builder = MaterialAlertDialogBuilder(activity)

            builder.setTitle(R.string.dialog_locked_state_title)
            builder.setMessage(R.string.dialog_locked_state_message)

            builder.create().apply {
                setCancelable(false)
                show()
            }
        }
    }

    fun showGuideDialog(activity: BaseActivity, viewModel: BooksViewModel) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_bottom_guide, null)

        val actionsDialog = BottomSheetDialog(activity)
        actionsDialog.setContentView(view)
        BottomSheetBehavior.from(view.parent as View).apply { peekHeight = 2000 }
        actionsDialog.show()

        view.dialogGuideButtonFinish.setOnClickListener {
            actionsDialog.dismiss()
        }

        actionsDialog.setOnDismissListener {
            PreferencesUtils.finishShowingGuide()
            viewModel.getBooks()
        }
    }

    private fun resolveServerError(errorBody: GenericError): Pair<Int, Int> {
        return when (errorBody.code) {
            ERROR_CODE_1 -> Pair(R.string.dialog_server_error_title_unauthorized_bad_credentials, R.string.dialog_server_error_message_unauthorized_bad_credentials)
            ERROR_CODE_102 -> Pair(R.string.dialog_server_error_title_words_incorrect, R.string.dialog_server_error_message_words_incorrect)
            ERROR_CODE_105 -> Pair(R.string.dialog_server_error_title_words_incorrect, R.string.dialog_server_error_message_words_incorrect)
            ERROR_CODE_203 -> Pair(R.string.dialog_server_error_title_books_max_limit, R.string.dialog_server_error_message_books_max_limit)
            else -> resolveHTTPError(errorBody.httpCode.toString())
        }
    }

    private fun resolveHTTPError(errorCode: String): Pair<Int, Int> {
        return when (errorCode) {
            "401" -> Pair(R.string.dialog_server_error_title_unauthorized, R.string.dialog_server_error_message_unauthorized)
            else -> Pair(R.string.dialog_server_error_title_generic, R.string.dialog_server_error_message_generic)
        }
    }

}