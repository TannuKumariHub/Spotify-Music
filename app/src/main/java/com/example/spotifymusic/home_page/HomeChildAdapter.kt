package com.example.spotifymusic.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.HomeChildItemBinding

class HomeChildAdapter(private val childList: List<HomeChildDataClass>,    private val onChildItemClick: (HomeChildDataClass) -> Unit
) :
    RecyclerView.Adapter<HomeChildAdapter.ViewHolder>() {

    class ViewHolder(val binding: HomeChildItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = childList[position]

        val imageUrl = currentItem.img.firstOrNull() ?: ""  // Fetch the first image URL

        Glide.with(holder.binding.root)
            .load(imageUrl)
            .into(holder.binding.homeChildImg)

        holder.binding.homeChildTxt.text = currentItem.txt

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Clicked item at position: $position", Toast.LENGTH_SHORT).show()

            onChildItemClick(currentItem)
        }
    }
}
