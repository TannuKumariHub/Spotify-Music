package com.example.spotifymusic.home_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using view binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val childItems1 = listOf(
            HomeChildDataClass("Item 1", R.drawable.tanu),
            HomeChildDataClass("Item 2", R.drawable.tanu),
            HomeChildDataClass("Item 3", R.drawable.tanu),
            HomeChildDataClass("Item 4", R.drawable.tanu)
        )

        val childItems2 = listOf(
            HomeChildDataClass("Item A", R.drawable.tanu),
            HomeChildDataClass("Item B", R.drawable.tanu),
            HomeChildDataClass("Item C", R.drawable.tanu),
            HomeChildDataClass("Item D", R.drawable.tanu)
        )

        val parentItems = listOf(
            HomeParentDataClass("Category 1", childItems1),
            HomeParentDataClass("Category 2", childItems2)
        )

        // Set up parent RecyclerView
        binding.recyclerMadeForYou.layoutManager = LinearLayoutManager(context)
        binding.recyclerMadeForYou.adapter = HomeParentAdapter(parentItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
