package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.R
import com.example.animeplex.adapter.SearchResultAdapter
import com.example.animeplex.databinding.ActivitySearchBinding
import com.example.animeplex.viewmodel.ExploreViewModel

class SearchActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySearchBinding
    private var exploreViewModel = ExploreViewModel()

    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchQuery = binding.searchQuery.text.toString().trim()

        binding.searchImg.setOnClickListener {
            searchQuery = binding.searchQuery.text.toString().trim()

            if (searchQuery.isNotEmpty()){
                exploreViewModel.getAnimeSearchResult(searchQuery)
                prepareResultRecyclerView(-1)
            }
        }

        binding.chipGroupSearch.setOnCheckedChangeListener {group, checkedId ->
            prepareResultRecyclerView(checkedId)
        }

        prepareResultRecyclerView(-1)

    }

    private fun prepareResultRecyclerView(checkedId: Int) {

        val searchResultAdapter = SearchResultAdapter {
            val intoDetail = Intent(this, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvSearchResult.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchResultAdapter
        }

        when (checkedId) {
            R.id.chip_anime -> {
                exploreViewModel.getAnimeSearchResult(searchQuery)
                exploreViewModel.observeAnimeSearchLiveData().observe(this) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
            R.id.chip_manga -> {
                exploreViewModel.getMangaSearchResult(searchQuery)
                exploreViewModel.observeMangaSearchLiveData().observe(this) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
            else -> {
                exploreViewModel.observeAnimeSearchLiveData().observe(this) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
        }

    }
}