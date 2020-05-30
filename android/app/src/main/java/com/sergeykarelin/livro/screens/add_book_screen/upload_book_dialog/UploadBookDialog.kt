package com.sergeykarelin.livro.screens.add_book_screen.upload_book_dialog

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Window
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.sergeykarelin.livro.Constants.Keys.KEY_ADD_BOOK_FILE
import com.sergeykarelin.livro.Constants.Keys.KEY_ADD_BOOK_PERMISSION
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.base.BaseActivity
import com.sergeykarelin.livro.services.extensions.getString
import com.sergeykarelin.livro.services.extensions.isValid
import com.sergeykarelin.livro.services.extensions.whenNotNull
import com.sergeykarelin.livro.services.rest.Result
import com.sergeykarelin.livro.services.utils.DialogUtils
import com.sergeykarelin.livro.services.utils.Utils
import kotlinx.android.synthetic.main.dialog_upload_book.*
import java.io.File

// TODO: Get rid of this idiotic library called PickIt, it could be replaced by SAF
class UploadBookDialog(private val activity: BaseActivity, private val uploadSuccessfulListener: () -> Unit) : Dialog(activity), PickiTCallbacks {

    private val pickIt by lazy { PickiT(activity, this) }
    private val viewModel by lazy { ViewModelProvider(activity).get(UploadBookViewModel::class.java) }
    private val neededPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    private var chosenFile: File? = null
    private var chosenFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_upload_book)

        initObservers()
        initClickListeners()
    }

    private fun initObservers() {
        viewModel.addBookResult.observe(activity, Observer { result ->
            when (result) {
                Result.SUCCESS -> {
                    Utils.showToast(R.string.add_book_status_uploaded)
                    uploadSuccessfulListener.invoke()
                    dismiss()
                }
                else -> {
                    DialogUtils.showServerErrorDialog(activity, result.errorCode)
                }
            }
        })
    }

    private fun initClickListeners() {
        uploadOwnBookChoose.setOnClickListener {
            try {
                if (checkPermissionGranted()) {
                    val intent = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                        Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    } else {
                        Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                    }

                    intent.type = "*/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.putExtra("return-data", true)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    activity.startActivityForResult(intent, KEY_ADD_BOOK_FILE)
                } else {
                    requestStoragePermission()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        uploadBookUpload.setOnClickListener {
            if (uploadBookTitle.isValid(activity.getString(R.string.upload_book_title))) {
                chosenFile.whenNotNull { file ->
                    chosenFileName.whenNotNull { fileName ->
                        viewModel.addNewBook(file, fileName, uploadBookTitle.getString())
                    }
                }
            }
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(activity, neededPermission, KEY_ADD_BOOK_PERMISSION)
    }

    private fun checkPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(activity, neededPermission.first()) == PackageManager.PERMISSION_GRANTED
    }

    override fun PickiTonStartListener() {}

    override fun PickiTonProgressUpdate(progress: Int) {}

    override fun PickiTonCompleteListener(path: String, wasDriveFile: Boolean, wasUnknownProvider: Boolean, wasSuccessful: Boolean, Reason: String) {
        if (path.isNotEmpty() && (path.contains(".txt") || path.contains(".epub"))) {
            chosenFileName = path.substring(path.lastIndexOf("/") + 1)
            chosenFile = File(path)

            uploadBookFile.text = chosenFileName
        } else {
            Utils.showToast(R.string.add_book_status_error_invalid_path)
        }
    }

    fun handleFileSelectionResult(data: Uri?) {
        pickIt.getPath(data, Build.VERSION.SDK_INT)
    }
}