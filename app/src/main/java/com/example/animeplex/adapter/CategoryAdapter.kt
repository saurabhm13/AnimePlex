package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.CategoryItemsBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<CategoryHome>()
    var onItemClick: ((CategoryHome) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<CategoryHome>){
        this.categoryList = categoryList as ArrayList<CategoryHome>
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.binding.imgCategory.setImageResource(categoryList[position].image)
//        holder.binding.imgCatgBackground.setColorFilter(R.color.catg_bag_color)

        holder.binding.categoryName.text = categoryList[position].name

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

}