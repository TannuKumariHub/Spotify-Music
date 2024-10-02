package com.example.spotifymusic.home_page


import androidx.annotation.Keep

@Keep
data class CategoryMadeForYouDataClass(
    val message: String,
    val playlists: Playlists
) {
    @Keep

    data class Playlists(
        val href: String,
        val items: List<Item>,
        val limit: Int,
        val next: Any,
        val offset: Int,
        val previous: Any,
        val total: Int
    ) {
        @Keep

        data class Item(
            val collaborative: Boolean,
            val description: String,
            val external_urls: ExternalUrls,
            val href: String,
            val id: String,
            val images: List<Image>,
            val name: String,
            val owner: Owner,
            val primary_color: String,
            val `public`: Boolean,
            val snapshot_id: String,
            val tracks: Tracks,
            val type: String,
            val uri: String
        ) {
            @Keep

            data class ExternalUrls(
                val spotify: String
            )

            @Keep

            data class Image(
                val height: Any,
                val url: String,
                val width: Any
            )

            @Keep

            data class Owner(
                val display_name: String,
                val external_urls: ExternalUrls,
                val href: String,
                val id: String,
                val type: String,
                val uri: String
            ) {
                @Keep

                data class ExternalUrls(
                    val spotify: String
                )
            }

            @Keep

            data class Tracks(
                val href: String,
                val total: Int
            )
        }
    }
}