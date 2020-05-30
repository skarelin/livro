package com.sergeykarelin.livro.screens.books_screen.listeners

import com.sergeykarelin.livro.data.dto.BookDTO

interface BooksEventsListener {

    fun deleteBook(book: BookDTO)

    fun openBook(book: BookDTO)

}