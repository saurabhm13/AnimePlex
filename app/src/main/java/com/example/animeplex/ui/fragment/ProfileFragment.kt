package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.R
import com.example.animeplex.adapter.ListAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.databinding.FragmentProfileBinding
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.ui.activity.AnimeMangaDetailActivity
import com.example.animeplex.viewmodel.HomeViewModel

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

        prepareMyAnimeListRecyclerView(-1)

        binding.chipGroupMyList.setOnCheckedChangeListener { group, checkedId ->
            prepareMyAnimeListRecyclerView(checkedId)
        }
        deleteAndUndoMyAnimeList()

        return binding.root
    }

    private fun prepareMyAnimeListRecyclerView(checkedId: Int) {

        val listAdapter = ListAdapter(
            onItemClick = {
                val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
                inToDetails.putExtra("id", it.mal_id.toString())
                inToDetails.putExtra("Content", it.type)
                startActivity(inToDetails)
            },
            onAddItemClick = {
                findNavController().navigate(R.id.homeFragment)
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