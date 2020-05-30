package com.sergeykarelin.livro.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
        @PrimaryKey val id: String,
        val title: String,
        val fileName: String,
        var currentPage: Int = 0
)