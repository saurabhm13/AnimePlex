package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeplex.R
import com.example.animeplex.adapter.ListAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.databinding.FragmentProfileBinding
import com.example.animeplex.db.AnimeDao
import com.example.animeplex.db.AnimeRepository
import com.example.animeplex.ui.activity.DetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.viewmodel.HomeViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareMyAnimeListRecyclerView(-1)

        binding.chipGroupMyList.setOnCheckedChangeListener { group, checkedId ->
            prepareMyAnimeListRecyclerView(checkedId)
        }
        deleteAndUndoMyAnimeList()
    }

    private fun prepareMyAnimeListRecyclerView(checkedId: Int) {

        val listAdapter = ListAdapter(
            onItemClick = {
                val inToDetails = Intent(activity, DetailActivity::class.java)
                inToDetails.putExtra("id", it.mal_id.toString())
                startActivity(inToDetails)
            },
            onAddItemClick = {
                findNavController().navigate(R.id.exploreFragment)
            }
        )

        binding.rvMyListProfile.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        when (checkedId) {
            R.id.chip_all -> {
                homeViewModel.observeAllMyListLiveData().observe(viewLifecycleOwner) {
                    listAdapter.setMyAnimeList(it)
                }
            }
            R.id.chip_anime -> {
                homeViewModel.observeMyAnimeListLiveData().observe(viewLifecycleOwner) {
                    listAdapter.setMyAnimeList(it)
                }
            }
            R.id.chip_manga -> {
                homeViewModel.observeMyMangaListLiveData().observe(viewLifecycleOwner) {
                    listAdapter.setMyAnimeList(it)
                }
            }
            else -> {
                homeViewModel.observeAllMyListLiveData().observe(viewLifecycleOwner) {
                    listAdapter.setMyAnimeList(it)
                }
            }
        }


    }

    private fun deleteAndUndoMyAnimeList() {

        val myAnimeListAdapter = MyAnimeListAdapter {

        }
    }

}