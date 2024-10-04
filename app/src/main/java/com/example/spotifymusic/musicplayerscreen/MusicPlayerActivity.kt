package com.example.spotifymusic.musicplayerscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {
    lateinit var binding:ActivityMusicPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMusicPlayerBinding.inflate(layoutInflater)
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
        val clickedPosition = bundle?.getInt("CLICKED_POSITION")


// Check that the lists are not null and have at least one item
        if (!trackNameList.isNullOrEmpty() && !artistNameList.isNullOrEmpty() && !imageUrlList.isNullOrEmpty()) {
            // Get the first item in the lists
            val trackName = trackNameList[clickedPosition!!]
            val artistName = artistNameList[clickedPosition]
            val imageUrl = imageUrlList[clickedPosition]

            // Log the first item's details
            Log.d("MusicPlayerActivity", "Track Name: $trackName")
            Log.d("MusicPlayerActivity", "Artist Name: $artistName")
            Log.d("MusicPlayerActivity", "Image URL: $imageUrl")

            // Set the TextViews with the track and artist names
            binding.musicPlayerSongName.text = trackName
            binding.musicPlayerArtistName.text = artistName

            // Load the image using Glide
            Glide.with(this)
                .load(imageUrl)
                .into(binding.musicPlayerImage)
        } else {
            Log.e("MusicPlayerActivity", "Received empty or null data from the intent")
        }

    }
}