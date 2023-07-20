package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.databinding.ActivityAnimeListByCategoryBinding
import com.example.animeplex.viewmodel.ExploreViewModel

class AnimeListByCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimeListByCategoryBinding
    val viewModel = ExploreViewModel()

    lateinit var categoryId: String
    lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeListByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        categoryId = intent.getStringExtra("Category Id")!!
        categoryName = intent.getStringExtra("Category Name")!!

        binding.textTopBar.text = categoryName

        viewModel.getAnimeByCategory(categoryId.toInt())
        prepareRecyclerView()



    }

    private fun prepareRecyclerView() {

        val animeAdapter = AnimeAdapter {
            val intoDetail = Intent(this, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = animeAdapter
        }

        viewModel.observeAnimeByCategoryLiveData().observe(this) {
            animeAdapter.setTopAnimeList(it.data)
        }
    }
}