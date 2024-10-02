package com.example.spotifymusic.home_page


import androidx.annotation.Keep

@Keep

data class CategoryMadeForYouItem(
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