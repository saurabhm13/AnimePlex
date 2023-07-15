package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.databinding.AnimeItemBinding
import kotlin.contracts.contract

class TopAnimeAdapter(): RecyclerView.Adapter<TopAnimeAdapter.TopAnimeViewHolder>() {

    private var topAnimeList = ArrayList<AnimeData>()


    @SuppressLint("NotifyDataSetChanged")
    fun setTopAnimeList(animeList: List<AnimeData>){
        this.topAnimeList = animeList as ArrayList<AnimeData>
        notifyDataSetChanged()
    }

    class TopAnimeViewHolder(val binding: AnimeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeViewHolder {
        return TopAnimeViewHolder(AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return topAnimeList.size
    }

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(topAnimeList[position].images.jpg.large_image_url)
            .into(holder.binding.imgAnime)
    }

}