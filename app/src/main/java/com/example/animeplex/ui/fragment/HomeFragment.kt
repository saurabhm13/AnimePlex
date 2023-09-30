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
import com.example.animeplex.ui.activity.AllCategoryActivity
import com.example.animeplex.ui.activity.AnimeListActivity
import com.example.animeplex.ui.activity.AnimeListByCategoryActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.ui.activity.AnimeMangaDetailActivity
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

        // Featured Anime
        if (imageList.isEmpty()) {
            viewModel.getFeaturedAnime()
            observeFeaturedAnime()
        }else {
            binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        }

        onFeaturedAnimeItemClick()

        // Category List
        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
        onCategoryItemClick()
        onMoreCategoryClick()

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

                if (isAdded) {
                    // Top Manga
                    viewModel.getTopManga()
                    prepareTopMangaRecyclerView()
                    onMoreTopMangaClick()
                }


            },2000
        )

        handler.postDelayed(
            Runnable {

                if (isAdded) {
                    // Upcoming Anime
                    viewModel.getUpcomingAnime()
                    prepareUpcomingAnimeRecyclerView()
                    onMoreUpComingAnimeClick()
                }


            },
            2500
        )

        handler.postDelayed(
            Runnable {

                if (isAdded) {
                    // Anime Movies
                    viewModel.getMovieAnime()
                    prepareMovieAnimeRecyclerView()
                    onMoreMovieAnimeClick()
                }


            },3000
        )

        handler.postDelayed(
            Runnable {

                if (isAdded) {
                    // Award Winning
                    viewModel.getAwardWinningAnime(46)
                    prepareAwardWinningAnimeRecyclerView()
                    onMoreAwardWinningClick()
                }


            },4000
        )

        handler.postDelayed(
            Runnable {

                if (isAdded) {
                    // Action Anime
                    viewModel.getActionAnime(1)
                    prepareActionAnimeRecyclerView()
                    onMoreActionAnimeClick()
                }

            },5000
        )

        return (binding.root)
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
                val intoDetail = Intent(activity, AnimeMangaDetailActivity::class.java)
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

    private fun onMoreCategoryClick() {
        binding.categoriesHome.setOnClickListener {
            val intoAllCategory = Intent(activity, AllCategoryActivity::class.java)
            startActivity(intoAllCategory)
        }

        binding.categoryMoreHome.setOnClickListener {
            val intoAllCategory = Intent(activity, AllCategoryActivity::class.java)
            startActivity(intoAllCategory)
        }
    }


    // Top Anime
    private fun prepareTopAnimeRecyclerView() {

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }

        binding.rvTopAnimeHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeTopAnimeLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
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

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Manga")
            startActivity(inToDetails)

        }

        binding.rvTopMangaHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
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

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }

        binding.rvUpComingHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeUpcomingAnimeLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
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
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", it.type)
            startActivity(inToDetails)
        }

        binding.rvMyListHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = myAnimeListAdapter
        }

        viewModel.observeAllMyListLiveData().observe(viewLifecycleOwner) {

            if (it.isEmpty()){
                binding.conMyList.visibility = View.GONE
            }else {
                myAnimeListAdapter.setMyAnimeList(it)
            }
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

    // Anime Movie
    private fun prepareMovieAnimeRecyclerView() {

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter {
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }

        binding.rvAnimeMovieHome.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeMovieAnimeLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
        }
    }

    private fun onMoreMovieAnimeClick() {
        binding.animeMovieHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Anime Movie")
            startActivity(intoAnimeList)
        }

        binding.animeMovieMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Anime Movie")
            startActivity(intoAnimeList)
        }
    }

    // Award Winning Anime
    private fun prepareAwardWinningAnimeRecyclerView() {

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter{
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }

        binding.rvAwardWinningAnimeHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeAwardWinningAnimeLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
        }
    }

    private fun onMoreAwardWinningClick() {
        binding.awardWinningAnimeHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Award Winning")
            startActivity(intoAnimeList)
        }

        binding.awardWinningAnimeMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Award Winning")
            startActivity(intoAnimeList)
        }
    }

    // Action Anime
    private fun prepareActionAnimeRecyclerView() {

        if (!isAdded) {
            return
        }

        val animeAdapter = AnimeAdapter{
            val inToDetails = Intent(activity, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }

        binding.rvActionAnimeHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter
        }

        if (isAdded) {
            viewModel.observeActionAnimeLiveData().observe(viewLifecycleOwner) {
                animeAdapter.setTopAnimeList(it.data)
            }
        }

    }

    private fun onMoreActionAnimeClick() {
        binding.actionAnimeHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Action Anime")
            startActivity(intoAnimeList)
        }

        binding.actionAnimeMoreHome.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Action Anime")
            startActivity(intoAnimeList)
        }
    }


    private fun onMoreClick() {
        val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
        intoAnimeList.putExtra("Category", "1")
        startActivity(intoAnimeList)
    }

}