package com.sergeykarelin.livro.data

import com.sergeykarelin.livro.R

data class BundledBook(val coverId: Int, val title: String, val author: String, val publicId: Int) {

    companion object {

        fun buildList(): List<BundledBook> {
            return listOf(
                    BundledBook(R.drawable.pic_bundled_book_1, "The Picture of Dorian Gray", "Oscar Wilde", 1),
                    BundledBook(R.drawable.pic_bundled_book_2, "Narrative of the Life of Frederick Douglass ", "Frederick Douglass", 2),
                    BundledBook(R.drawable.pic_bundled_book_3, "The Treasure Train", "Arthur B. Reeve", 3),
                    BundledBook(R.drawable.pic_bundled_book_4, "The Way We Live Now", "Anthony Trollope", 4),
                    BundledBook(R.drawable.pic_bundled_book_5, "The Destroying Angel", "Louis Joseph Vance", 5)
            )
        }

    }

}