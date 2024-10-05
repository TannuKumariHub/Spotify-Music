package com.example.spotifymusic.home_page

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.FragmentHomeBinding
import com.example.spotifymusic.playlistscreen.PlayListFragment
import com.example.spotifymusic.spotify_api.SpotifyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*   val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.getDefault())
           val currentDateAndTime = sdf.format(Date())*/

        val hour = SimpleDateFormat("HH", Locale.getDefault()).format(Date()).toInt()

        if (hour == 12) {
            binding.txtGreeting.text = "Good Afternoon"
        } else if (hour < 12) {
            binding.txtGreeting.text = "Good Morning"
        } else {
            binding.txtGreeting.text = "Good Evening"
        }



        sharedPreferences = activity?.getSharedPreferences("SpotifyShared", Context.MODE_PRIVATE)!!
        val accessToken = sharedPreferences.getString("TOKEN_STORED", null)

        if (accessToken.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Access Token is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val authorizationHeader = "Bearer $accessToken"

        // Set up the parent RecyclerView
        binding.recyclerMadeForYou.layoutManager = LinearLayoutManager(requireContext())
        val parentItems = mutableListOf<HomeParentDataClass>()

        fetchCategoryPlaylists(
            "0JQ5DAt0tbjZptfcdMSKl3",
            "Made For You",
            parentItems,
            authorizationHeader
        )
        fetchCategoryPlaylists(
            "0JQ5DAqbMKFQIL0AXnG5AK",
            "Trending Now",
            parentItems,
            authorizationHeader
        )
    }

    // Function to fetch playlists and update the parent item list
    private fun fetchCategoryPlaylists(
        categoryId: String,
        categoryName: String,
        parentItems: MutableList<HomeParentDataClass>,
        authorizationHeader: String
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)

        retrofit.getPlaylistsByCategory(categoryId, authorizationHeader)
            .enqueue(object : Callback<CategoryMadeForYouDataClass?> {
                override fun onResponse(
                    call: Call<CategoryMadeForYouDataClass?>,
                    response: Response<CategoryMadeForYouDataClass?>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val playlists = data?.playlists?.items ?: emptyList()

                        if (playlists.isNotEmpty()) {
                            val childItems = playlists.map { playlist ->
                                HomeChildDataClass(
                                    playlist.name,
                                    playlist.images.map { it.url },
                                    playlist.id

                                )
                            }
                            parentItems.add(HomeParentDataClass(categoryName, childItems))

                            val parentAdapter = HomeParentAdapter(parentItems,
                                onHomeParentItemClick = { parentItem ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Clicked on Parent: ${parentItem.txt}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onHomeChildItemClick = { childItem ->
                                    val playlistId = childItem.id
                                    val imageUrl = if (childItem.img.isNotEmpty()) childItem.img[0] else null
                                    val txt = childItem.txt
                                    Log.d("oo", "maya: $imageUrl")

                                    // Create a new instance of PlayListFragment and pass the ID
                                    val playListFragment = PlayListFragment().apply {
                                        arguments = Bundle().apply {
                                            putString("PLAYLIST_ID", playlistId)
                                            putString("PLAYLIST_IMAGE", imageUrl) // Key should be consistent
                                            putString("TEXT", txt)
                                        }
                                    }

                                    // Perform fragment transaction to replace the current fragment with PlayListFragment
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, playListFragment)
                                        .addToBackStack(null) // Optional: Add to back stack to allow navigating back
                                        .commit()

                                    Log.d("TAG", "Navigating to PlayListFragment with playlistId: $playlistId")
                                }
                            )



                            binding.recyclerMadeForYou.adapter = parentAdapter
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "No playlists found in $categoryName",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("HomeFragment", "No playlists found in $categoryName")
                        }
                    } else {
                        Log.e(
                            "HomeFragment",
                            "Failed to fetch category: ${response.code()} - ${
                                response.errorBody()?.string()
                            }"
                        )
                        Toast.makeText(
                            requireContext(),
                            "Error fetching playlists: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CategoryMadeForYouDataClass?>, t: Throwable) {
                    Log.e("HomeFragment", "API call failed: ${t.localizedMessage}")
                    Toast.makeText(
                        requireContext(),
                        "API call failed: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
