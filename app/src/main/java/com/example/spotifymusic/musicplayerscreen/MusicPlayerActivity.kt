package com.example.spotifymusic.musicplayerscreen

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.ActivityMusicPlayerBinding
import com.example.spotifymusic.spotify_api.SpotifyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityMusicPlayerBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val bundle = intent.extras

// Retrieve the lists from the bundle
        val trackNameList = bundle?.getStringArrayList("TRACK_NAMES")
        val artistNameList = bundle?.getStringArrayList("ARTIST_NAMES")
        val imageUrlList = bundle?.getStringArrayList("IMAGE_URLS")
        val idList = bundle?.getStringArrayList("ID")
        Log.d("qqqqqqqqqq", "onCreate: $idList")
        val clickedPosition = bundle?.getInt("CLICKED_POSITION")


// Check that the lists are not null and have at least one item
//        if (!trackNameList.isNullOrEmpty() && !artistNameList.isNullOrEmpty() && !imageUrlList.isNullOrEmpty()) {
        // Get the first item in the lists
        val trackName = trackNameList?.get(clickedPosition!!)
        val artistName = clickedPosition?.let { artistNameList?.get(it) }
        val imageUrl = clickedPosition?.let { imageUrlList?.get(it) }
        val id = clickedPosition?.let { idList?.get(it) }

        // Log the first item's details
        Log.d("MusicPlayerActivity", "Track Name: $trackName")
        Log.d("MusicPlayerActivity", "Artist Name: $artistName")
        Log.d("MusicPlayerActivity", "Image URL: $imageUrl")
        Log.d("MusicPlayerActivity", "Image URL: $id")

        // Set the TextViews with the track and artist names
        binding.musicPlayerSongName.text = trackName
        binding.musicPlayerArtistName.text = artistName

        // Load the image using Glide
        Glide.with(this)
            .load(imageUrl)
            .into(binding.musicPlayerImage)
        /*  } else {
              Log.e("MusicPlayerActivity", "Received empty or null data from the intent")
          }*/

        sharedPreferences = getSharedPreferences("SpotifyShared", Context.MODE_PRIVATE)!!
        val accessToken = sharedPreferences.getString("TOKEN_STORED", null)

        if (accessToken.isNullOrEmpty()) {
            Toast.makeText(this, "Access Token is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val authorizationHeader = "Bearer $accessToken"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)


        if (id != null) {
            retrofit.getTracks(authorizationHeader,id).enqueue(object : Callback<TracksSeveral?> {
                override fun onResponse(p0: Call<TracksSeveral?>, p1: Response<TracksSeveral?>) {
                    val data=p1.body()
                    val song= data?.tracks?.get(0)?.preview_url
                    mediaPlayer = MediaPlayer().apply {
                        setAudioStreamType(AudioManager.STREAM_MUSIC)
                        try {
                            setDataSource(song)
                            prepare()
                            start()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }

                override fun onFailure(p0: Call<TracksSeveral?>, p1: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
/*
        if (id != null) {
            retrofit.getTrack(authorizationHeader, id).enqueue(object : Callback<TrackDataClass?> {
                override fun onResponse(
                    p0: Call<TrackDataClass?>,
                    response: Response<TrackDataClass?>
                ) {
                    val data = response.body()
                    Log.d("MusicPlayerActivity", "onResponse: $data")

                    val song = data?.external_urls.toString()
                    Log.d("ww", "onResponse: $song")

//                    playSongFromUrl(song)

                                         mediaPlayer = MediaPlayer().apply {
                                             setAudioStreamType(AudioManager.STREAM_MUSIC)
                                             try {
                                                 setDataSource("")
                                                 prepare()
                                                 start()
                                             }catch (e:Exception){
                                                e.printStackTrace()
                                             }
                                             Toast.makeText(this@MusicPlayerActivity, "audio start", Toast.LENGTH_SHORT).show()

 prepareAsync()  // Prepares the player asynchronously (important for streaming)
                         setOnPreparedListener {
                             it.start()  // Start playing once the MediaPlayer is prepared
                         }

                     }


                }

                override fun onFailure(p0: Call<TrackDataClass?>, p1: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
*/

    }

    /*    private fun playSongFromUrl(url: String) {
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                try {
                    setDataSource(url)  // URL must point to a valid MP3 stream
                    prepareAsync()
                    setOnPreparedListener {
                        start()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MusicPlayerActivity, "Failed to play song", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
}