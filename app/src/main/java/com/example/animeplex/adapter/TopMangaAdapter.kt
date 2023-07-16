package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeData
import com.example.animeplex.databinding.AnimeItemBinding

class TopMangaAdapter(): RecyclerView.Adapter<TopMangaAdapter.TopMangaViewHolder>() {

    private var topMangaList = ArrayList<AnimeData>()

    lateinit var onItemClick: ((AnimeData) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setTopMangaList(topMangaList: List<AnimeData>){
        this.topMangaList = topMangaList as ArrayList<AnimeData>
        notifyDataSetChanged()
    }

    class TopMangaViewHolder(val binding: AnimeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMangaViewHolder {
        return TopMangaViewHolder(AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return topMangaList.size
    }

    override fun onBindViewHolder(holder: TopMangaViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(topMangaList[position].images.jpg.large_image_url)
            .into(holder.binding.imgAnime)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(topMangaList[position])
        }
    }

}