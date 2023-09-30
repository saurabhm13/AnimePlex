package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.R
import com.example.animeplex.adapter.SearchResultAdapter
import com.example.animeplex.databinding.FragmentSearchBinding
import com.example.animeplex.ui.activity.AnimeMangaDetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.viewmodel.HomeViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        searchQuery = binding.searchQuery.text.toString().trim()

        binding.searchImg.setOnClickListener {
            searchQuery = binding.searchQuery.text.toString().trim()

            if (searchQuery.isNotEmpty()){
                viewModel.getAnimeSearchResult(searchQuery)
                prepareResultRecyclerView(-1)
            }
        }

        binding.chipGroupSearch.setOnCheckedChangeListener {group, checkedId ->
            prepareResultRecyclerView(checkedId)
        }

        prepareResultRecyclerView(-1)


        return binding.root
    }

    private fun prepareResultRecyclerView(checkedId: Int) {

        val intoDetail = Intent(activity, AnimeMangaDetailActivity::class.java)

        val searchResultAdapter = SearchResultAdapter {

            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvSearchResult.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchResultAdapter
        }

        when (checkedId) {
            R.id.chip_anime -> {
                intoDetail.putExtra("Content", "Anime")
                viewModel.getAnimeSearchResult(searchQuery)
                viewModel.observeAnimeSearchLiveData().observe(viewLifecycleOwner) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
            R.id.chip_manga -> {
                intoDetail.putExtra("Content", "Manga")
                viewModel.getMangaSearchResult(searchQuery)
                viewModel.observeMangaSearchLiveData().observe(viewLifecycleOwner) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
            else -> {
                intoDetail.putExtra("Content", "Anime")
                viewModel.observeAnimeSearchLiveData().observe(viewLifecycleOwner) {
                    searchResultAdapter.setSearchList(it.data)
                    binding.chipGroupSearch.visibility = View.VISIBLE
                }
            }
        }

    }

}