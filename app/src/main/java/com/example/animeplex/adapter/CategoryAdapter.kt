package com.example.animeplex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeplex.R
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.CategoryItemsBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewModel>() {

    private var categoryList = ArrayList<CategoryHome>()
    var onItemClick: ((CategoryHome) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<CategoryHome>){
        this.categoryList = categoryList as ArrayList<CategoryHome>
        notifyDataSetChanged()
    }

    class CategoryViewModel(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewModel {
        return CategoryViewModel(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewModel, position: Int) {

        holder.binding.imgCategory.setImageResource(categoryList[position].image)
        holder.binding.catgBackground.setImageResource(R.drawable.category_bag)

        holder.binding.categoryName.text = categoryList[position].name

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

}