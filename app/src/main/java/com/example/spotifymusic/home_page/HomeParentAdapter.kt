package com.example.spotifymusic.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifymusic.databinding.HomeChildItemBinding
import com.example.spotifymusic.databinding.HomeParentItemBinding

class HomeParentAdapter(val ParentList:List<HomeParentDataClass>) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {
    class ViewHolder(val binding: HomeParentItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=HomeParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ParentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=ParentList[position]
        holder.binding.homeParentTxt.text=currentItem.txt
        holder.binding.recyclerTrendingNow.layoutManager = LinearLayoutManager(holder.binding.homeParentTxt.context, LinearLayoutManager.HORIZONTAL, false)

        // Here we use the ChildAdapter to populate the child RecyclerView
        holder.binding.recyclerTrendingNow.adapter = HomeChildAdapter(currentItem.childitem)
    }
}