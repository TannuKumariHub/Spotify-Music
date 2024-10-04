package com.example.spotifymusic.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifymusic.databinding.HomeParentItemBinding

class HomeParentAdapter(private val ParentList: List<HomeParentDataClass?>,private val onHomeParentItemClick: (HomeParentDataClass) -> Unit,
                        private val onHomeChildItemClick: (HomeChildDataClass) -> Unit) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {
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
        if (currentItem != null) {
            holder.binding.homeParentTxt.text=currentItem.txt
        }

        holder.itemView.setOnClickListener {
            if (currentItem != null) {
                onHomeParentItemClick(currentItem)
            }
        }

        holder.binding.recyclerTrendingNow.layoutManager = LinearLayoutManager(holder.binding.homeParentTxt.context, LinearLayoutManager.HORIZONTAL, false)

        // Here we use the ChildAdapter to populate the child RecyclerView
        if (currentItem != null) {
            holder.binding.recyclerTrendingNow.adapter = HomeChildAdapter(currentItem.childitem){
                    childItem ->
                onHomeChildItemClick(childItem)

            }
        }
    }
}