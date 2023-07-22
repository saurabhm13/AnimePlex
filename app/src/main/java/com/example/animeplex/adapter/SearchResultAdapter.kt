package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeData
import com.example.animeplex.databinding.AnimeItemBinding
import com.example.animeplex.databinding.CharacterItem1ListBinding


class SearchResultAdapter(
    private val onItemClick: (AnimeData) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private var searchList = ArrayList<AnimeData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchList(animeList: List<AnimeData>) {
        searchList.clear()
        searchList.addAll(animeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = CharacterItem1ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = searchList[position]
        holder.bind(item)
    }

    inner class SearchResultViewHolder(private val binding: CharacterItem1ListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeData) {
            Glide.with(binding.root)
                .load(item.images.jpg.large_image_url)
                .into(binding.animeImage)

            val title = item.title_english

            if (title.isNullOrEmpty()){
                binding.animeName.text = item.title
            }else {
                binding.animeName.text = item.title_english
            }

            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}