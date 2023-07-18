package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.databinding.AnimeItemBinding

class AnimeAdapter(private val onItemClick: (AnimeData) -> Unit) : RecyclerView.Adapter<AnimeAdapter.TopAnimeViewHolder>() {

    private var topAnimeList = ArrayList<AnimeData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTopAnimeList(animeList: List<AnimeData>) {
        topAnimeList.clear()
        topAnimeList.addAll(animeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeViewHolder {
        val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopAnimeViewHolder(binding)
    }

    override fun getItemCount(): Int = topAnimeList.size

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {
        val item = topAnimeList[position]
        holder.bind(item)
    }

    inner class TopAnimeViewHolder(private val binding: AnimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeData) {
            Glide.with(binding.root)
                .load(item.images.jpg.large_image_url)
                .into(binding.imgAnime)

            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}