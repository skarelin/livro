package com.sergeykarelin.livro.services.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.getSpans
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.services.utils.Utils

fun EditText.isValid(text: String): Boolean {
    return if (getText().toString().isEmpty()) {
        val message = String.format(LivroApplication.context.getString(R.string.helper_missing_field), text)
        Utils.showToast(message)
        false
    } else {
        true
    }
}

fun EditText.getString() = this.text.toString()

fun TextView.clearHighlights() {
    val parent = this.text as SpannableString

    val allSpannables = parent.getSpans<BackgroundColorSpan>()

    allSpannables.forEach {
        parent.removeSpan(it)
    }
}

fun Spannable.highlightText(location: Pair<Int, Int>) {
    val highlightSpan = BackgroundColorSpan(ContextCompat.getColor(LivroApplication.context, R.color.colorReaderHighlight))
    this.setSpan(highlightSpan, location.first, location.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
}