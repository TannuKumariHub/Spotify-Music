package com.example.spotifymusic.searchpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifymusic.databinding.HomeParentItemBinding
import com.example.spotifymusic.databinding.SearchParentItemBinding
import com.example.spotifymusic.home_page.HomeChildAdapter
import com.example.spotifymusic.home_page.HomeParentDataClass

class SearchParentAdapter(val searchList: List<SearchParentDataClass>):
    RecyclerView.Adapter<SearchParentAdapter.ViewHolder>() {
    class ViewHolder (val binding: SearchParentItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
return searchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=searchList[position]
        if (currentItem != null) {
            holder.binding.searchParentTxt.text=currentItem.txt
        }
        holder.binding.recyclerViewBrowse.layoutManager = LinearLayoutManager(holder.binding.searchParentTxt.context, LinearLayoutManager.HORIZONTAL, false)

        // Here we use the ChildAdapter to populate the child RecyclerView
        if (currentItem != null) {
            holder.binding.recyclerViewBrowse.adapter = SearchChildAdapter(currentItem.childitem)
        }
    }

    /*
        class HomeParentAdapter(val ParentList: List<HomeParentDataClass?>) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {
            class ViewHolder(val binding: HomeParentItemBinding): RecyclerView.ViewHolder(binding.root) {

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val binding=
                    HomeParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
                holder.binding.recyclerTrendingNow.layoutManager = LinearLayoutManager(holder.binding.homeParentTxt.context, LinearLayoutManager.HORIZONTAL, false)

                // Here we use the ChildAdapter to populate the child RecyclerView
                if (currentItem != null) {
                    holder.binding.recyclerTrendingNow.adapter = HomeChildAdapter(currentItem.childitem)
                }
            }
        }
    */
}