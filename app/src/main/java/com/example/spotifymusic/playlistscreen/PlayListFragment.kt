package com.example.spotifymusic.playlistscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.FragmentPlayListBinding


class PlayListFragment : Fragment() {
    private var _binding: FragmentPlayListBinding? = null
    private val binding get() = _binding!!

    lateinit var playListAdapter: PlayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false)
        playListAdapter= PlayListAdapter(listOf())
        binding.recyclerPlayList.adapter=playListAdapter
        binding.recyclerPlayList.layoutManager=LinearLayoutManager(context)
    }

}