package com.example.spotifymusic.playlistscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifymusic.databinding.PlayListItemBinding

class PlayListAdapter(
    val PlayList: List<PlayListDataClassTrack.Item.Track>,
    val onPLayListItemClick: (PlayListDataClassTrack.Item.Track,Int) -> Unit
) :
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {
    class ViewHolder(val binding: PlayListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PlayListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PlayList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemPosition = PlayList[position]


        Glide.with(holder.binding.root)
            .load(currentItemPosition.album.images[0].url)
            .into(holder.binding.playListItemImg)
        holder.itemView.setOnClickListener {
            onPLayListItemClick(currentItemPosition,position)
        }
        holder.binding.playListItemTitle.text = currentItemPosition.name
        holder.binding.playListItemSingerName.text = currentItemPosition.artists[0].name
//        holder.binding.playListItemMore.setImageResource(currentItemPosition.threedot)
    }
}