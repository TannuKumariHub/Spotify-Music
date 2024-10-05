package com.example.spotifymusic.spotify_api


import UserProfile
import com.example.spotifymusic.home_page.CategoryItem
import com.example.spotifymusic.home_page.CategoryMadeForYouDataClass
import com.example.spotifymusic.home_page.CategoryMadeForYouItem
import com.example.spotifymusic.musicplayerscreen.TrackDataClass
import com.example.spotifymusic.musicplayerscreen.TracksSeveral
import com.example.spotifymusic.playlistscreen.PlayListDataClassTrack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {
    @GET("me")
    fun getCurrentUserProfile(@Header("Authorization") authorization: String): Call<UserProfile>

    @GET("browse/categories")
    fun getCategories(
        @Header("Authorization") authorization: String, // Pass the token in the header
        @Query("locale") locale: String // Query parameter for locale, e.g. "en_IN"
    ): Call<CategoryItem>

    @GET("browse/categories/{category_id}")
    fun getCategoryMadeForYou(
        @Header("Authorization") authorization: String,
        @Path("category_id") categoryId: String
    ): Call<CategoryMadeForYouItem>

    @GET("browse/categories/{category_id}/playlists")
    fun getPlaylistsByCategory(
        @Path("category_id") categoryId: String,
        @Header("Authorization") authToken: String
    ): Call<CategoryMadeForYouDataClass>

    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(
        @Path("playlist_id") playlistId: String,
        @Header("Authorization") authorization: String
    ): Call<PlayListDataClassTrack>

    @GET("tracks/{id}")
    fun getTrack(
        @Header("Authorization") authToken: String,
        @Path("id") trackId: String
    ): Call<TrackDataClass>

    @GET("tracks")
    fun getTracks(
        @Header("Authorization") authHeader: String,
        @Query("ids") trackIds: String // Comma-separated track IDs
    ): Call<TracksSeveral>

}
