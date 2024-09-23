package com.example.spotifymusic.playlistscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifymusic.databinding.PlayListItemBinding

class PlayListAdapter(val PlayList: List<PlayListDataClass>) :
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
        holder.binding.playListItemImg.setImageResource(currentItemPosition.img)
        holder.binding.playListItemTitle.text = currentItemPosition.headertxt
        holder.binding.playListItemSingerName.text = currentItemPosition.nametxt
        holder.binding.playListItemMore.setImageResource(currentItemPosition.threedot)
    }
}