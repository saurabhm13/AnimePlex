package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.databinding.AnimeItemBinding


class UpcomingAnimeAdapter(): RecyclerView.Adapter<UpcomingAnimeAdapter.UpComingAnimeViewHolder>() {

    private var upcomingAnimeList = ArrayList<AnimeData>()
    lateinit var onItemClick: ((AnimeData) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setUpcomingAnime(upComingAnimeList: List<AnimeData>){
        this.upcomingAnimeList = upComingAnimeList as ArrayList<AnimeData>
        notifyDataSetChanged()
    }

    class UpComingAnimeViewHolder(val binding: AnimeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingAnimeViewHolder {
        return UpComingAnimeViewHolder(AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return upcomingAnimeList.size
    }

    override fun onBindViewHolder(holder: UpComingAnimeViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(upcomingAnimeList[position].images.jpg.large_image_url)
            .into(holder.binding.imgAnime)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(upcomingAnimeList[position])
        }
    }

}