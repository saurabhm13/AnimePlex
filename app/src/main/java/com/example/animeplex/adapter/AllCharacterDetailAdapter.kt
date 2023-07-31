package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.Data
import com.example.animeplex.databinding.CharacterItem1ListBinding

class AllCharacterDetailAdapter(): RecyclerView.Adapter<AllCharacterDetailAdapter.CharacterDetailViewHolder>() {

    private var characterList = ArrayList<Data>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(characterList: List<Data>){
        this.characterList = characterList as ArrayList<Data>
        notifyDataSetChanged()
    }

    class CharacterDetailViewHolder(val binding: CharacterItem1ListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDetailViewHolder {
        return CharacterDetailViewHolder(CharacterItem1ListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterDetailViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(characterList[position].character.images.jpg.image_url)
            .into(holder.binding.animeImage)

        holder.binding.animeName.text = characterList[position].character.name

    }
}