package com.sergeykarelin.livro.screens.add_book_screen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.sergeykarelin.livro.R
import com.sergeykarelin.livro.data.BundledBook
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_book_bundled.*

class BundledBooksAdapter(private val data: List<BundledBook>, private val downloadClickListener: (BundledBook) -> Unit) : RecyclerView.Adapter<BundledBooksAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book_bundled, parent, false)

        val viewHolder = ViewHolder(itemView)

        viewHolder.bookBundledDownload.setOnClickListener {
            val position = viewHolder.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                downloadClickListener.invoke(data[position])
            }
        }

        return viewHolder
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(bundledBook: BundledBook) {
            bookBundledCover.load(bundledBook.coverId)

            bookBundledTitle.text = bundledBook.title
            bookBundledAuthor.text = bundledBook.author
        }

    }
}