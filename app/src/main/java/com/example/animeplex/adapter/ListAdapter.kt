package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.databinding.AddItemBinding
import com.example.animeplex.databinding.AnimeItemBinding

class ListAdapter(
    private val onItemClick: (AnimeDataToSave) -> Unit,
    private val onAddItemClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myAnimeList = ArrayList<AnimeDataToSave>()

    // View types
    private val VIEW_TYPE_ADD = 0
    private val VIEW_TYPE_REGULAR = 1

    @SuppressLint("NotifyDataSetChanged")
    fun setMyAnimeList(animeList: List<AnimeDataToSave>) {
        myAnimeList.clear()
        myAnimeList.addAll(animeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD -> {
                val binding =
                    AddItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AddViewHolder(binding)
            }

            VIEW_TYPE_REGULAR -> {
                val binding =
                    AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RegularViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int {
        // Add 1 for the "Add" item
        return myAnimeList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_ADD
        } else {
            VIEW_TYPE_REGULAR
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddViewHolder -> holder.bindAddItem()
            is RegularViewHolder -> holder.bind(myAnimeList[position - 1])
        }
    }

    inner class AddViewHolder(private val binding: AddItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindAddItem() {
            binding.root.setOnClickListener {
                onAddItemClick.invoke()
            }
        }
    }

    inner class RegularViewHolder(private val binding: AnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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