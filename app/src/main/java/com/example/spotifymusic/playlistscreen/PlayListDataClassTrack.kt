package com.example.spotifymusic.playlistscreen


import androidx.annotation.Keep

@Keep

data class PlayListDataClassTrack(
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
        val added_at: String,
        val added_by: AddedBy,
        val is_local: Boolean,
        val primary_color: Any,
        val track: Track,
        val video_thumbnail: VideoThumbnail
    ) {

        @Keep
        data class AddedBy(
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
        data class Track(
            val album: Album,
            val artists: List<Artist>,
            val available_markets: List<String>,
            val disc_number: Int,
            val duration_ms: Int,
            val episode: Boolean,
            val explicit: Boolean,
            val external_ids: ExternalIds,
            val external_urls: ExternalUrls,
            val href: String,
            val id: String,
            val is_local: Boolean,
            val name: String,
            val popularity: Int,
            val preview_url: String,
            val track: Boolean,
            val track_number: Int,
            val type: String,
            val uri: String
        ) {
            @Keep
            data class Album(
                val album_type: String,
                val artists: List<Artist>,
                val available_markets: List<String>,
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

        @Keep

        data class VideoThumbnail(
            val url: Any
        )
    }
}