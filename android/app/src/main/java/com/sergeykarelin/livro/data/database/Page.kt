package com.sergeykarelin.livro.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Page(
        @PrimaryKey val number: Int,
        val bookId: String,
        val page: String
)