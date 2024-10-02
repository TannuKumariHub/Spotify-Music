package com.example.spotifymusic.spotify_api


import UserProfile
import com.example.spotifymusic.home_page.CategoryItem
import com.example.spotifymusic.home_page.CategoryMadeForYouDataClass
import com.example.spotifymusic.home_page.CategoryMadeForYouItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale.Category

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
}
