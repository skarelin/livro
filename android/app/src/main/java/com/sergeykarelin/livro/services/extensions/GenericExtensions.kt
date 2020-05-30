package com.sergeykarelin.livro.services.extensions

import android.content.Context
import android.content.Intent
import com.sergeykarelin.livro.services.rest.GenericError
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import kotlin.reflect.KClass

private val SPECIAL_CHARACTERS = Regex("[!@#\$%^&*(),.?\":;{}|<>]")

inline fun <T : Any, R> T?.whenNotNull(callback: (T) -> R): R? {
    return this?.let(callback)
}

fun File.copyToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}

fun ResponseBody?.toGenericError(): GenericError {
    try {
        this.whenNotNull {
            val errorJSON = JSONObject(it.string())
            return GenericError(errorJSON.getString("errorCode"),
                    errorJSON.getString("message"),
                    errorJSON.getInt("httpCode"))
        }
    } catch (e: Exception) {
        return GenericError()
    }

    return GenericError()
}

fun Context.startActivity(activity: KClass<*>) {
    this.startActivity(Intent(this, activity.java))
}

fun String.prepareForServer() = this.replace(SPECIAL_CHARACTERS, "").trim()