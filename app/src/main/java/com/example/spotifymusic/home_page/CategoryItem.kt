package com.example.spotifymusic.home_page


import androidx.annotation.Keep

@Keep

data class CategoryItem(
    val categories: Categories
) {
    @Keep

    data class Categories(
        val href: String,
        val items: List<Item>,
        val limit: Int,
        val next: String,
        val offset: Int,
        val previous: Any,
        val total: Int
    ) {
        @Keep

        data class Item(
            val href: String,
            val icons: List<Icon>,
            val id: String,
            val name: String
        ) {
            @Keep

            data class Icon(
                val height: Int,
                val url: String,
                val width: Int
            )
        }
    }
}