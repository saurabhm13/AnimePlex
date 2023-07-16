package com.example.animeplex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.CategoryItemsBinding

class AllCategoryAdapter(): RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder>() {

    private var allCategoryList = ArrayList<CategoryHome>()

    class AllCategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryViewHolder {
        return AllCategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return allCategoryList.size
    }

    override fun onBindViewHolder(holder: AllCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(allCategoryList[position].image)
            .into(holder.binding.imgCategory)

        holder.binding.categoryName.text = allCategoryList[position].name
    }
}