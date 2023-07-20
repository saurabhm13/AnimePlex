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
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.CategoryAdapter
import com.example.animeplex.adapter.MyAnimeListAdapter
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.FragmentHomeBinding
import com.example.animeplex.ui.activity.AnimeListActivity
import com.example.animeplex.ui.activity.AnimeListByCategoryActivity
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

        // Top Anime
        viewModel.getTopAnime()
        prepareTopAnimeRecyclerView()
        onMoreTopAnimeClick()

        // My Anime List
        prepareMyAnimeListRecyclerView()
        onMoreMyAnimeClick()


        // Using delay of 2 sec for Upcoming anime because api handles 3 request/sec
        val handler = Handler()

        handler.postDelayed(
            Runnable {

                // Top Manga
                viewModel.getTopManga()
                prepareTopMangaRecyclerView()
                onMoreTopMangaClick()

            },1000
        )

        handler.postDelayed(
            Runnable {

                // Upcoming Anime
                viewModel.getUpcomingAnime()
                prepareUpcomingAnimeRecyclerView()
                onMoreUpComingAnimeClick()

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
            val intoAnimeByCategory = Intent(activity, AnimeListByCategoryActivity::class.java)
            intoAnimeByCategory.putExtra("Category Id", it.id.toString())
            intoAnimeByCategory.putExtra("Category Name", it.name)
            startActivity(intoAnimeByCategory)
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

    private fun onMoreTopAnimeClick() {

        binding.topAnimeHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Anime")
//            intoAnimeList.putExtra("Category", "1")
            startActivity(intoAnimeList)
        }

        binding.topAnimeMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Anime")
            startActivity(intoAnimeList)
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

    private fun onMoreTopMangaClick() {
        binding.topMangaHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Manga")
            startActivity(intoAnimeList)
        }

        binding.topMangaMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Manga")
            startActivity(intoAnimeList)
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

    private fun onMoreUpComingAnimeClick() {
        binding.upComingHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Upcoming Anime")
            startActivity(intoAnimeList)
        }

        binding.upComingMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Upcoming Anime")
            startActivity(intoAnimeList)
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

    private fun onMoreMyAnimeClick() {
        binding.myListHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "My Anime List")
            startActivity(intoAnimeList)
        }

        binding.myListMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "My Anime List")
            startActivity(intoAnimeList)
        }
    }

    private fun onMoreClick() {
        val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
        intoAnimeList.putExtra("Category", "1")
        startActivity(intoAnimeList)
    }

}