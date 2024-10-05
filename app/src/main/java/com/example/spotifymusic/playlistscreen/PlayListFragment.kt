package com.example.spotifymusic.playlistscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.FragmentPlayListBinding
import com.example.spotifymusic.musicplayerscreen.MusicPlayerActivity
import com.example.spotifymusic.spotify_api.SpotifyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayListFragment : Fragment() {
    private var _binding: FragmentPlayListBinding? = null

    private lateinit var sharedPreferences: SharedPreferences

    private val binding get() = _binding!!

    lateinit var playListAdapter: PlayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentPlayListBinding.inflate(inflater, container, false)
        binding.backPlaylist.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = activity?.getSharedPreferences("SpotifyShared", Context.MODE_PRIVATE)!!
        val accessToken = sharedPreferences.getString("TOKEN_STORED", null)

        if (accessToken.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Access Token is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val authorizationHeader = "Bearer $accessToken"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)

        val playlistId = arguments?.getString("PLAYLIST_ID")
        val playlisttxt = arguments?.getString("TEXT")
        binding.playlistTxt.text = playlisttxt
        val playlistImageUrl = arguments?.getString("PLAYLIST_IMAGE") // Use the same key
        Log.d("PlayListFragment", "Received playlist image URL: $playlistImageUrl")

        if (!playlistImageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(playlistImageUrl)
                .placeholder(R.drawable.tanu) // Use a placeholder image while loading
                .error(R.drawable.ic_launcher_background) // Fallback in case of error
                .into(binding.playlistImage)
        } else {
            Log.d("PlayListFragment", "Image URL is null or empty")
        }




        if (playlistId != null) {
            retrofit.getPlaylistTracks(playlistId, authorizationHeader)
                .enqueue(object : Callback<PlayListDataClassTrack?> {
                    override fun onResponse(
                        p0: Call<PlayListDataClassTrack?>,
                        response: Response<PlayListDataClassTrack?>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            Log.d("yyyyyy", "onResponse: $data")

                            val playListItem = data?.items?.map { it.track }
                            Log.d("iiii", "onResponse: $playListItem")

//                        playListAdapter = playListItem?.let { PlayListAdapter(it.) }!!

                            playListItem?.let { tracks ->
                                // Initialize empty ArrayLists outside the click listener
                                val arrayNameList = ArrayList<String>()
                                val arrayArtistNameList = ArrayList<String>()
                                val arrayImageList = ArrayList<String>()

                                val arrayId = ArrayList<String>()

                                // Populate the lists with all track details
                                tracks.forEach { track ->
                                    arrayNameList.add(track.name)
                                    arrayArtistNameList.add(track.artists[0].name)
//                                val rrr=track.track

                                    arrayId.add(track.id)
                                    Log.d("rrrrrrr", "onResponse: ${track.id}")


                                    val imageUrl = if (track.album.images.isNotEmpty()) {
                                        track.album.images[0].url
                                    } else {
                                        "default_image_url" // Placeholder or default URL
                                    }
                                    arrayImageList.add(imageUrl)

                                    // Log the details added to the lists
                                    Log.d(
                                        "TrackList",
                                        "Added track: ${track.name}, artist: ${track.artists[0].name}, imageUrl: $imageUrl"
                                    )
                                }

                                // Log the entire lists
                                Log.d("TrackList", "All track names: $arrayNameList")
                                Log.d("TrackList", "All artist names: $arrayArtistNameList")
                                Log.d("TrackList", "All image URLs: $arrayImageList")
                                Log.d("TrackList", "All image URLs: $arrayId")


                                // Set up the adapter
                                playListAdapter = PlayListAdapter(tracks) { track, position ->


                                    // Create a bundle and pass the ArrayLists and clicked position
                                    val bundle = Bundle()
                                    bundle.putStringArrayList("TRACK_NAMES", arrayNameList)
                                    bundle.putStringArrayList("ARTIST_NAMES", arrayArtistNameList)
                                    bundle.putStringArrayList("IMAGE_URLS", arrayImageList)
                                    bundle.putStringArrayList("ID", arrayId)
                                    bundle.putInt(
                                        "CLICKED_POSITION",
                                        position
                                    ) // Pass the clicked position

                                    // Log the data being passed to the intent
                                    Log.d("IntentData", "Passing track names: $arrayNameList")
                                    Log.d(
                                        "IntentData",
                                        "Passing artist names: $arrayArtistNameList"
                                    )
                                    Log.d("IntentData", "Passing image URLs: $arrayImageList")
                                    Log.d("IntentData", "Clicked position: $position")

                                    // Create an intent and start the MusicPlayerActivity
                                    val intent = Intent(context, MusicPlayerActivity::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)

                                    // Log which track was clicked
                                    Log.d(
                                        "ItemClick",
                                        "Clicked on: ${track.name}, Position: $position"
                                    )
                                }

                                binding.recyclerPlayList.adapter = playListAdapter
                                binding.recyclerPlayList.layoutManager =
                                    LinearLayoutManager(context)
                            }

                        }
                    }


                    override fun onFailure(p0: Call<PlayListDataClassTrack?>, p1: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks by clearing binding
    }
}
