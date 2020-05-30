package com.sergeykarelin.livro.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithPages(
        @Embedded val book: Book,
        @Relation(
                parentColumn = "id",
                entityColumn = "bookId",
                entity = Page::class
        )
        val pages: List<Page>
)