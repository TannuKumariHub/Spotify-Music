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
import com.example.spotifymusic.spotify_api.SpotifyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        sharedPreferences = activity?.getSharedPreferences("SpotifyShared", Context.MODE_PRIVATE)!!
        val accessToken = sharedPreferences.getString("TOKEN_STORED", null)

        if (accessToken.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Access Token is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val authorizationHeader = "Bearer $accessToken"
        Log.d("eeeeeeeeee", "onViewCreated: $authorizationHeader")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)

        val categoryItem_Id = "0JQ5DAt0tbjZptfcdMSKl3"

        retrofit.getPlaylistsByCategory(categoryItem_Id,authorizationHeader)
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
                                    playlist.name ,
                                    R.drawable.tanu
                                )
                            }

                            val parentItems = listOf(
                                HomeParentDataClass(
                                    "Made For You",  // Label for the parent item
                                    childItems
                                )
                            )

                            binding.recyclerMadeForYou.layoutManager = LinearLayoutManager(requireContext())
                            binding.recyclerMadeForYou.adapter = HomeParentAdapter(parentItems)
                        } else {
                            Toast.makeText(requireContext(), "No playlists found", Toast.LENGTH_SHORT).show()
                            Log.e("HomeFragment", "No playlists found")
                        }
                    } else {
                        Log.e("HomeFragment", "Failed to fetch category: ${response.code()} - ${response.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error fetching playlists: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CategoryMadeForYouDataClass?>, t: Throwable) {
                    Log.e("HomeFragment", "API call failed: ${t.localizedMessage}")
                    Toast.makeText(requireContext(), "API call failed: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
