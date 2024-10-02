package com.example.spotifymusic.searchpage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.FragmentSearchBinding
import com.example.spotifymusic.home_page.CategoryItem
import com.example.spotifymusic.spotify_api.SpotifyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("SpotifyShared", Context.MODE_PRIVATE)!!
        val accessToken = sharedPreferences.getString("TOKEN_STORED", "Nhi Mila")
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

/*
        retrofit.getCategories(authorizationHeader, "en_IN").enqueue(object : Callback<CategoryItem?> {
            override fun onResponse(call: Call<CategoryItem?>, response: Response<CategoryItem?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val items = data?.categories?.items ?: emptyList()

                    if (items.isNotEmpty()) {
                        // Map each item to SearchChildDataClass
                        val childItems = items.map { category ->
                            SearchChildDataClass(
                                category.name ?: "Unknown", // Use the category name or fallback to "Unknown"
                                R.drawable.tanu // Static drawable for now
                            )
                        }

                        // Create the parent data class list
                        val parentItems = listOf(
                            SearchParentDataClass(
                                "Categories", // Parent label
                                childItems // Pass the entire list of child items
                            )
                        )

                        // Setup RecyclerView
                        binding.recyclerViewYourTopGenres.layoutManager = GridLayoutManager(requireContext(),2)
                        binding.recyclerViewYourTopGenres.adapter = SearchParentAdapter(parentItems)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
                    Log.e("SearchFragment", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CategoryItem?>, t: Throwable) {
                Toast.makeText(requireContext(), "API call failed: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("SearchFragment", "API call failed: ${t.localizedMessage}")
            }
        })
*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
