package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.animeplex.R
import com.example.animeplex.adapter.CategoryAdapter
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

    lateinit var topAnimeAdapter: TopAnimeAdapter
    private val topAnimeList = ArrayList<AnimeData>()

    lateinit var topMangaAdapter: TopMangaAdapter

    lateinit var upcomingAnimeAdapter: UpcomingAnimeAdapter

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

        // Top Anime
        viewModel.getTopAnime()
        prepareTopAnimeRecyclerView()
        observeTopAnimeLiveData()
        onTopAnimeItemClick()


        // Using delay of 2 sec for Upcoming anime because api handles 3 request/sec
        val handler = Handler()

        handler.postDelayed(
            Runnable {

                // Top Manga
                viewModel.getTopManga()
                prepareTopMangaRecyclerView()
                observeTopMangaLiveData()
                onTopMangaItemClick()

            },1000
        )

        handler.postDelayed(
            Runnable {


                // Upcoming Anime
                viewModel.getUpcomingAnime()
                prepareUpcomingAnimeRecyclerView()
                observeUpcomingAnimeLiveData()
                onUpcomingAnimeItemClick()
            },
            2500
        )

    }

    // Featured Anime
    private fun createCategoryList() {

        categoryList.add(CategoryHome(R.drawable.action, "Action"))
        categoryList.add(CategoryHome(R.drawable.adv, "Adventure"))
        categoryList.add(CategoryHome(R.drawable.comedy, "Comedy"))
        categoryList.add(CategoryHome(R.drawable.drama, "Drama"))
        categoryList.add(CategoryHome(R.drawable.fantasy, "Fantasy"))
    }

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

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            startActivity(intoAnimeList)
        }
    }


    // Top Anime
    private fun prepareTopAnimeRecyclerView() {
        topAnimeAdapter = TopAnimeAdapter()
        binding.rvTopAnimeHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topAnimeAdapter
        }
    }

    private fun observeTopAnimeLiveData() {
        viewModel.observeTopAnimeLiveData().observe(viewLifecycleOwner){
            topAnimeAdapter.setTopAnimeList(it.data)
        }
    }

    private fun onTopAnimeItemClick() {
        topAnimeAdapter.onItemClick = {
            val inToDetails = Intent(activity, DetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id)
            startActivity(inToDetails)
        }
    }

    // Top Manga
    private fun prepareTopMangaRecyclerView(){
        topMangaAdapter = TopMangaAdapter()
        binding.rvTopMangaHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topMangaAdapter
        }
    }

    private fun observeTopMangaLiveData(){
        viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner){
            topMangaAdapter.setTopMangaList(it.data)
        }
    }

    private fun onTopMangaItemClick() {
        topMangaAdapter.onItemClick = {
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id)
            startActivity(intoDetail)
        }
    }

    // Upcoming Anime
    private fun prepareUpcomingAnimeRecyclerView() {
        upcomingAnimeAdapter = UpcomingAnimeAdapter()
        binding.rvUpComingHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAnimeAdapter
        }
    }

    private fun observeUpcomingAnimeLiveData() {
        viewModel.observeUpcomingAnimeLiveData().observe(viewLifecycleOwner){
            upcomingAnimeAdapter.setUpcomingAnime(it.data)
        }
    }

    private fun onUpcomingAnimeItemClick() {
        upcomingAnimeAdapter.onItemClick = {
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id)
            startActivity(intoDetail)
        }
    }
}