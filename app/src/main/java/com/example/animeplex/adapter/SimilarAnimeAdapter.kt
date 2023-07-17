package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.SimilarAnimaData
import com.example.animeplex.data.SimilarAnime
import com.example.animeplex.databinding.AnimeItemBinding

class SimilarAnimeAdapter(): RecyclerView.Adapter<SimilarAnimeAdapter.SimilarAnimeViewHolder>() {


    private var similarAnimeList = ArrayList<SimilarAnimaData>()
    lateinit var onItemClick: ((SimilarAnimaData) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setSimilarAnimeList(similarAnimeList: List<SimilarAnimaData>) {
        this.similarAnimeList = similarAnimeList as ArrayList<SimilarAnimaData>
        notifyDataSetChanged()
    }


    class SimilarAnimeViewHolder(val binding: AnimeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarAnimeViewHolder {
        return SimilarAnimeViewHolder(AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return if (similarAnimeList.size > 10){
            10
        }else {
            similarAnimeList.size
        }
    }

    override fun onBindViewHolder(holder: SimilarAnimeViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(similarAnimeList[position].entry.images.jpg.image_url)
            .into(holder.binding.imgAnime)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(similarAnimeList[position])
        }
    }

}