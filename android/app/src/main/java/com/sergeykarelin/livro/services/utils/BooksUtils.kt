package com.sergeykarelin.livro.services.utils

import android.widget.TextView
import com.github.mertakdut.Reader
import com.github.mertakdut.exception.OutOfPagesException
import com.sergeykarelin.livro.Constants.EMPTY_STRING
import com.sergeykarelin.livro.LivroApplication
import com.sergeykarelin.livro.data.database.BookWithPages
import com.sergeykarelin.livro.data.database.Page
import java.io.File

object BooksUtils {

    private const val LINE_SEPARATOR = "\n\n"

    val parentStorage: File = LivroApplication.context.getExternalFilesDir(null)
            ?: LivroApplication.context.filesDir

    fun splitBookInPages(book: BookWithPages, textView: TextView): List<Page> {
        val pageSplitter = PageSplitter(textView.width, textView.height, 1.0f, 0.0f).apply {
            append(getBookFile("${book.book.id}.txt"))
            split(textView.paint)
        }

        return pageSplitter.getPages().mapIndexed { index, charSequence -> Page(index, book.book.id, charSequence.toString()) }
    }

    private fun getBookFile(bookFileName: String): String {
        val file = File(parentStorage.absolutePath + "/" + bookFileName)
        return when (file.extension) {
            "txt" -> file.readTXT()
            "epub" -> file.readEPUB()
            else -> EMPTY_STRING
        }
    }

    private fun File.readEPUB(): String {
        val reader = Reader().apply {
            setMaxContentPerSection(1000)
            setIsIncludingTextContent(true)
            setFullContent(this@readEPUB.absolutePath)
        }

        val stringBuilder = StringBuilder()
        try {
            for (i in 0 until Int.MAX_VALUE) {
                val bookSection = reader.readSection(i)
                stringBuilder.append(bookSection.sectionTextContent)
            }

            stringBuilder.toString()
        } catch (e: OutOfPagesException) {
            return stringBuilder.toString()
        }

        return stringBuilder.toString()
    }

    private fun File.readTXT(): String {
        val stringBuilder = StringBuilder()
        this.forEachLine { stringBuilder.append(if (it.trim().isNotEmpty()) " $it" else LINE_SEPARATOR) }
        return stringBuilder.toString()
    }

}