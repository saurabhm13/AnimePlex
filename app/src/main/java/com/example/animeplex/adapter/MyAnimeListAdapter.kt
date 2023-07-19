package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.databinding.AnimeItemBinding

class MyAnimeListAdapter(private val onItemClick: (AnimeDataToSave) -> Unit) : RecyclerView.Adapter<MyAnimeListAdapter.MyAnimeListViewHolder>() {

    private var myAnimeList = ArrayList<AnimeDataToSave>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMyAnimeList(animeList: List<AnimeDataToSave>) {
        myAnimeList.clear()
        myAnimeList.addAll(animeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAnimeListViewHolder {
        val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAnimeListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myAnimeList.size
    }

    override fun onBindViewHolder(holder: MyAnimeListViewHolder, position: Int) {
        val item = myAnimeList[position]
        holder.bind(item)
    }

    inner class MyAnimeListViewHolder(private val binding: AnimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeDataToSave) {
            Glide.with(binding.root)
                .load(item.image)
                .into(binding.imgAnime)

            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}