package com.sergeykarelin.livro.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergeykarelin.livro.data.database.Book
import com.sergeykarelin.livro.data.database.Page
import com.sergeykarelin.livro.database.dao.BooksDao

@Database(entities = [Book::class, Page::class], version = 2)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao

    companion object {

        const val NAME = "books-database"

    }

}