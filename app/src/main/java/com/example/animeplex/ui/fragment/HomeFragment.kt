package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.animeplex.R
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.CategoryAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.adapter.TopAnimeAdapter
import com.example.animeplex.adapter.TopMangaAdapter
import com.example.animeplex.adapter.UpcomingAnimeAdapter
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.FragmentHomeBinding
import com.example.animeplex.ui.activity.AllCategoryActivity
import com.example.animeplex.ui.activity.AnimeListActivity
import com.example.animeplex.ui.activity.DetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    private val imageList = ArrayList<SlideModel>()

    lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = ArrayList<CategoryHome>()

    private lateinit var myAnimeListAdapter: MyAnimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Featured Anime
        viewModel.getFeaturedAnime()
        observeFeaturedAnime()
        onFeaturedAnimeItemClick()

        // Category List
        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
        onCategoryItemClick()

        // My Anime List
        prepareMyAnimeListRecyclerView()


        // Using delay of 2 sec for Upcoming anime because api handles 3 request/sec
        val handler = Handler()

        handler.postDelayed(
            Runnable {

                // Top Anime
                viewModel.getTopAnime()
                prepareTopAnimeRecyclerView()

                // Top Manga
                viewModel.getTopManga()
                prepareTopMangaRecyclerView()

            },1000
        )

        handler.postDelayed(
            Runnable {

                // Upcoming Anime
                viewModel.getUpcomingAnime()
                prepareUpcomingAnimeRecyclerView()
            },
            2500
        )

    }

    // Featured Anime
    private fun observeFeaturedAnime() {
        viewModel.observeFeaturedAnimeLiveData().observe(viewLifecycleOwner){

            imageList.add(SlideModel(it.data[0].images.jpg.large_image_url))
            imageList.add(SlideModel(it.data[1].images.jpg.large_image_url))
            imageList.add(SlideModel(it.data[2].images.jpg.large_image_url))

            binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        }
    }

    private fun onFeaturedAnimeItemClick() {
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val intoDetail = Intent(activity, DetailActivity::class.java)
                // ToDo Send "id"
                startActivity(intoDetail)
            }
        })
    }

    // Category
    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategoryHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun createCategoryList() {

        categoryList.add(CategoryHome(1, R.drawable.action, "Action"))
        categoryList.add(CategoryHome(2, R.drawable.adv, "Adventure"))
        categoryList.add(CategoryHome(4, R.drawable.comedy, "Comedy"))
        categoryList.add(CategoryHome(8, R.drawable.drama, "Drama"))
        categoryList.add(CategoryHome(10, R.drawable.fantasy, "Fantasy"))
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            startActivity(intoAnimeList)
        }
    }


    // Top Anime
    private fun prepareTopAnimeRecyclerView() {

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToDetails)
        }

        binding.rvTopAnimeHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        viewModel.observeTopAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter.setTopAnimeList(it.data)
        }
    }

    // Top Manga
    private fun prepareTopMangaRecyclerView(){

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToDetails)
        }

        binding.rvTopMangaHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner) {
            animeAdapter.setTopAnimeList(it.data)
        }
    }

    // Upcoming Anime
    private fun prepareUpcomingAnimeRecyclerView() {

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToDetails)
        }

        binding.rvUpComingHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        viewModel.observeUpcomingAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter.setTopAnimeList(it.data)
        }
    }

    // My Anime List
    private fun prepareMyAnimeListRecyclerView() {

        myAnimeListAdapter = MyAnimeListAdapter {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToDetails)
        }

        binding.rvMyListHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = myAnimeListAdapter
        }

        viewModel.observeMyAnimeListLiveData().observe(viewLifecycleOwner) {
            myAnimeListAdapter.setMyAnimeList(it)
        }
    }
}