package com.sergeykarelin.livro.database.dao

import androidx.room.*
import com.sergeykarelin.livro.data.database.Book
import com.sergeykarelin.livro.data.database.BookWithPages
import com.sergeykarelin.livro.data.database.Page

@Dao
interface BooksDao {

    @Transaction
    @Query("SELECT * FROM book WHERE id=:id")
    suspend fun getBookById(id: String): BookWithPages

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllBooks(books: List<Book>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPages(pages: List<Page>)

    @Query("UPDATE Book SET currentPage = :page WHERE id = :bookId")
    suspend fun updatePage(bookId: String, page: Int)

    @Delete
    suspend fun removeBook(book: Book)

}