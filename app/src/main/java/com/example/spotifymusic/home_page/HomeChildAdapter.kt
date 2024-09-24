package com.example.spotifymusic.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifymusic.databinding.HomeChildItemBinding

class HomeChildAdapter (val ChildList:List<HomeChildDataClass>): RecyclerView.Adapter<HomeChildAdapter.ViewHolder>() {
    class ViewHolder(val binding:HomeChildItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=HomeChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ChildList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentItem=ChildList[position]
    holder.binding.homeChildImg.setImageResource(currentItem.img)
    holder.binding.homeChildTxt.text=currentItem.txt
    }
}