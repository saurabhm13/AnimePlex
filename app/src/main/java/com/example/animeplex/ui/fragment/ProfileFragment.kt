package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeplex.R
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.databinding.FragmentProfileBinding
import com.example.animeplex.ui.activity.DetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var myAnimeListAdapter: MyAnimeListAdapter
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

        prepareMyAnimeListRecyclerView()

        deleteAndUndoMyAnimeList()

    }

    private fun prepareMyAnimeListRecyclerView() {

        val myAnimeListAdapter = MyAnimeListAdapter {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToDetails)
        }

        binding.rvMyListProfile.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = myAnimeListAdapter
        }

        homeViewModel.observeMyAnimeListLiveData().observe(viewLifecycleOwner) {
            myAnimeListAdapter.setMyAnimeList(it)
        }
    }

    private fun deleteAndUndoMyAnimeList() {

        val myAnimeListAdapter = MyAnimeListAdapter {

        }
    }

}