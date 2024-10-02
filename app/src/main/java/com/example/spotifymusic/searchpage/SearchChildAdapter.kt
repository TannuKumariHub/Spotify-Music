package com.example.spotifymusic.searchpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifymusic.databinding.SearchChildItemBinding
import kotlin.contracts.contract

class SearchChildAdapter(val searchchildList: List<SearchChildDataClass>):
    RecyclerView.Adapter<SearchChildAdapter.ViewHolder>() {
    class ViewHolder(val binding: SearchChildItemBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
return searchchildList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=searchchildList[position]
//        holder.binding.searchChildItemImg.setImageResource(currentItem.img)
        Glide.with(holder.binding.root).load(currentItem.img).into(holder.binding.searchChildItemImg);

        holder.binding.searchChildItemTxt.text=currentItem.txt
    }
}
/*class HomeChildAdapter (private val ChildList:List<HomeChildDataClass>): RecyclerView.Adapter<HomeChildAdapter.ViewHolder>() {
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
}*/