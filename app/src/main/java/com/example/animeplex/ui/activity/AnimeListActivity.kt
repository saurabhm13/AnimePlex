package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.databinding.ActivityAnimeListBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.ExploreViewModel
import com.example.animeplex.viewmodel.HomeViewModel
import com.example.animeplex.viewmodel.HomeViewModelFactory

class AnimeListActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimeListBinding
    private var viewModel = ExploreViewModel()

    private lateinit var homeViewModel: HomeViewModel

    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)

        homeViewModel = HomeViewModel(animeDatabase)

        val intent = intent
        type = intent.getStringExtra("Type")!!

        binding.textTopBar.text = type

//        viewModel.getAnimeByCategory(category.toInt())
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

        when (type) {
            "Top Anime" -> {

                homeViewModel.getTopAnime()

                homeViewModel.observeTopAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "Top Manga" -> {

                homeViewModel.getTopManga()

                homeViewModel.observeTopMangaLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "Upcoming Anime" -> {

                homeViewModel.getUpcomingAnime()

                homeViewModel.observeUpcomingAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "My Anime List" -> {

                prepareMyAnimeListRecyclerView()
            }
            "Anime Movie" -> {

                viewModel.getMovieAnime()
                viewModel.observeMovieAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }
            }
            "Award Winning" -> {

                viewModel.getAwardWinningAnime(46)
                viewModel.observeAwardWinningAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }
            }
            "Action Anime" -> {

                viewModel.getAnimeByCategory(1)
                viewModel.observeAnimeByCategoryLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }
            }
            "Category" -> {

                val intoAllCategory = Intent(this, AllCategoryActivity::class.java)
                startActivity(intoAllCategory)
            }
        }
//        viewModel.observeAnimeByCategoryLiveData().observe(this) {
//            animeAdapter.setTopAnimeList(it.data)
//        }

    }

    private fun prepareMyAnimeListRecyclerView() {

        homeViewModel.observeMyAnimeListLiveData()

        val myAnimeListAdapter = MyAnimeListAdapter{
            val intoDetail = Intent(this, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = myAnimeListAdapter
        }

        homeViewModel.observeMyAnimeListLiveData().observe(this) {
            myAnimeListAdapter.setMyAnimeList(it)
        }

    }
}