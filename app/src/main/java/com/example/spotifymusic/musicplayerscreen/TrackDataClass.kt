package com.example.spotifymusic.musicplayerscreen


import androidx.annotation.Keep

@Keep

data class TrackDataClass(
    val album: Album,
    val artists: List<Artist>,
    val available_markets: List<Any>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalIds,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val name: String,
    val popularity: Int,
    val track_number: Int,
    val type: String,
    val uri: String
) {
    @Keep

    data class Album(
        val album_type: String,
        val artists: List<Artist>,
        val available_markets: List<Any>,
        val external_urls: ExternalUrls,
        val href: String,
        val id: String,
        val images: List<Image>,
        val name: String,
        val release_date: String,
        val release_date_precision: String,
        val total_tracks: Int,
        val type: String,
        val uri: String
    ) {

        @Keep
        data class Artist(
            val external_urls: ExternalUrls,
            val href: String,
            val id: String,
            val name: String,
            val type: String,
            val uri: String
        ) {

            @Keep
            data class ExternalUrls(
                val spotify: String
            )
        }

        @Keep

        data class ExternalUrls(
            val spotify: String
        )
        @Keep
        data class Image(
            val height: Int,
            val url: String,
            val width: Int
        )
    }

    @Keep

    data class Artist(
        val external_urls: ExternalUrls,
        val href: String,
        val id: String,
        val name: String,
        val type: String,
        val uri: String
    ) {
        @Keep

        data class ExternalUrls(
            val spotify: String
        )
    }

    @Keep

    data class ExternalIds(
        val isrc: String
    )

    @Keep
        data class ExternalUrls(
        val spotify: String
    )
}