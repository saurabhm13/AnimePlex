package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.databinding.ActivityAnimeListBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.HomeViewModel

class AnimeListActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimeListBinding

    private lateinit var viewModel: HomeViewModel

    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)

        viewModel = HomeViewModel(animeDatabase)

        val intent = intent
        type = intent.getStringExtra("Type")!!

        binding.textTopBar.text = type

//        viewModel.getAnimeByCategory(category.toInt())
        prepareRecyclerView()

    }

    private fun prepareRecyclerView() {

        val intoDetail = Intent(this, AnimeMangaDetailActivity::class.java)

        val animeAdapter = AnimeAdapter {
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = animeAdapter
        }

        when (type) {
            "Top Anime" -> {

                intoDetail.putExtra("Content", "Anime")

                viewModel.getTopAnime()

                viewModel.observeTopAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "Top Manga" -> {

                intoDetail.putExtra("Content", "Manga")
                viewModel.getTopManga()

                viewModel.observeTopMangaLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "Upcoming Anime" -> {


                intoDetail.putExtra("Content", "Anime")
                viewModel.getUpcomingAnime()

                viewModel.observeUpcomingAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }

            }
            "My Anime List" -> {

                prepareMyAnimeListRecyclerView()
            }
            "Anime Movie" -> {

                intoDetail.putExtra("Content", "Anime")
                viewModel.getMovieAnime()
                viewModel.observeMovieAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }
            }
            "Award Winning" -> {

                intoDetail.putExtra("Content", "Anime")
                viewModel.getAwardWinningAnime(46)
                viewModel.observeAwardWinningAnimeLiveData().observe(this) {
                    animeAdapter.setTopAnimeList(it.data)
                }
            }
            "Action Anime" -> {

                intoDetail.putExtra("Content", "Anime")
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

        viewModel.observeMyAnimeListLiveData()

        val myAnimeListAdapter = MyAnimeListAdapter{
            val intoDetail = Intent(this, AnimeMangaDetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            intoDetail.putExtra("Content", it.type)
            startActivity(intoDetail)
        }

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = myAnimeListAdapter
        }

        viewModel.observeMyAnimeListLiveData().observe(this) {
            myAnimeListAdapter.setMyAnimeList(it)
        }

    }
}