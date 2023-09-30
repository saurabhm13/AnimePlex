package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.databinding.ActivityAnimeListByCategoryBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.HomeViewModel

class AnimeListByCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimeListByCategoryBinding
    private lateinit var viewModel: HomeViewModel

    lateinit var categoryId: String
    lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeListByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)

        viewModel = HomeViewModel(animeDatabase)

        val intent = intent
        categoryId = intent.getStringExtra("Category Id")!!
        categoryName = intent.getStringExtra("Category Name")!!

        binding.textTopBar.text = categoryName

        viewModel.getAnimeByCategory(categoryId.toInt())
        prepareRecyclerView()



    }

    private fun prepareRecyclerView() {

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(this, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
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