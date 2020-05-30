package com.sergeykarelin.livro.screens.books_screen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.data.dto.BookDTO
import com.sergeykarelin.livro.screens.books_screen.listeners.BooksEventsListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_book.*

class BooksAdapter(private val data: MutableList<BookDTO>, private val listener: BooksEventsListener) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    fun removeItem(bookId: String) {
        val position = data.indexOf(data.find { it.id == bookId })

        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(override val containerView: View, private val listener: BooksEventsListener) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(book: BookDTO) {
            bookTitle.text = book.title

            bootNavigationDelete.setOnClickListener { listener.deleteBook(book) }
            bookNavigationRoot.setOnClickListener { listener.openBook(book) }
        }

    }
}