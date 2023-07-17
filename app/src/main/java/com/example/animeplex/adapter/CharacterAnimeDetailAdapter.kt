package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.Data
import com.example.animeplex.databinding.AnimeItemBinding
import com.example.animeplex.databinding.CharacterItem2ListBinding

class CharacterAnimeDetailAdapter(): RecyclerView.Adapter<CharacterAnimeDetailAdapter.CharacterADViewHolder>() {

    private var characterArrayList = ArrayList<Data>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(characterArrayList: List<Data>) {
        this.characterArrayList = characterArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    class CharacterADViewHolder(val binding: AnimeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterADViewHolder {
        return CharacterADViewHolder(AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return if (characterArrayList.size > 10){
            10
        }else {
            characterArrayList.size
        }
    }

    override fun onBindViewHolder(holder: CharacterADViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(characterArrayList[position].character.images.jpg.image_url)
            .into(holder.binding.imgAnime)
    }

}