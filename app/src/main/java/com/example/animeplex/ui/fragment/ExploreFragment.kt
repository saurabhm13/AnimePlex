package com.example.animeplex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeplex.R
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.CategoryAdapter
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.FragmentExploreBinding
import com.example.animeplex.ui.activity.AnimeListActivity
import com.example.animeplex.ui.activity.DetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.viewmodel.ExploreViewModel
import com.example.animeplex.viewmodel.HomeViewModel

class ExploreFragment : Fragment() {


    lateinit var binding: FragmentExploreBinding
    lateinit var viewModel: HomeViewModel

    private var exploreViewModel = ExploreViewModel()
//    lateinit var movieAnimeAdapter: TopAnimeAdapter

    lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = ArrayList<CategoryHome>()

    lateinit var animeAdapter: AnimeAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Movie Anime
        exploreViewModel.getMovieAnime()
        prepareMovieAnimeRecyclerView()
        observeMovieAnimeLiveData()
//        onMovieAnimeItemClick()

        // Category
        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
        onCategoryItemClick()

        // Top Manga
        viewModel.getTopManga()
        prepareTopMangaRecyclerView()
        observeTopMangaLiveData()
//        onTopMangaItemClick()

        // Using delay of 2 sec for Upcoming anime because api handles 3 request/sec
        val handler = Handler()

        handler.postDelayed(
            Runnable {

                // Award winning
                exploreViewModel.getAnimeByCategory(46)
                prepareAwardWinningAnimeRecyclerView()

                // Top Anime
                prepareTopAnimeRecyclerView()

            },1000
        )

        handler.postDelayed(
            Runnable {

                // Action Anime
                exploreViewModel.getAnimeByCategory(1)
                prepareActionAnimeRecyclerView()

                // UpComing anime
                viewModel.getUpcomingAnime()
                prepareUpComingAnimeRecyclerView()

            }, 1000
        )


    }
    
    private fun prepareMovieAnimeRecyclerView() {

//        movieAnimeAdapter = TopAnimeAdapter()
//        binding.rvAnimeMovieExplore.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            binding.rvAnimeMovieExplore.adapter = movieAnimeAdapter
//        }

        val animeAdapter1 = AnimeAdapter{
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvAnimeMovieExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter1
        }

        exploreViewModel.observeMovieAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter1.setTopAnimeList(it.data)
        }

    }

    private fun observeMovieAnimeLiveData() {

//        exploreViewModel.observeMovieAnimeLiveData().observe(viewLifecycleOwner) {
//            movieAnimeAdapter.setTopAnimeList(it.data)
//        }
    }

    private fun onMovieAnimeItemClick() {
//        movieAnimeAdapter.onItemClick = {
//            val intoDetail = Intent(activity, DetailActivity::class.java)
//            intoDetail.putExtra("id", it.mal_id.toString())
//            startActivity(intoDetail)
//        }
    }

    // Category
    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategoryExplore.apply {
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

    // Top Manga
    private fun prepareTopMangaRecyclerView() {

//        movieAnimeAdapter = TopAnimeAdapter()
//        binding.rvTopMangaExplore.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            binding.rvTopMangaExplore.adapter = movieAnimeAdapter
//        }

        val animeAdapter2 = AnimeAdapter {
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvTopMangaExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter2
        }

        viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner) {
            animeAdapter2.setTopAnimeList(it.data)
        }
    }

    private fun observeTopMangaLiveData() {

//        viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner) {
//            movieAnimeAdapter.setTopAnimeList(it.data)
//        }
    }

    private fun onTopMangaItemClick() {
//        movieAnimeAdapter.onItemClick = {
//            val intoDetail = Intent(activity, DetailActivity::class.java)
//            intoDetail.putExtra("id", it.mal_id.toString())
//            startActivity(intoDetail)
//        }
    }

    // Top Anime
    private fun prepareTopAnimeRecyclerView() {
        val animeAdapter3 = AnimeAdapter{
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvTopAnimeExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter3
        }

        viewModel.observeTopAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter3.setTopAnimeList(it.data)
        }
    }

    // Award Winning
    private fun prepareAwardWinningAnimeRecyclerView() {

        val animeAdapter4 = AnimeAdapter{
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvAwardWinningAnimeExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter4
        }

        exploreViewModel.observeAnimeByCategoryLiveData().observe(viewLifecycleOwner) {
            animeAdapter4.setTopAnimeList(it.data)
        }
    }

    // Action Anime
    private fun prepareActionAnimeRecyclerView() {

        val animeAdapter5 = AnimeAdapter{
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvActionAnimeExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter5
        }

        exploreViewModel.observeAnimeByCategoryLiveData().observe(viewLifecycleOwner) {
            animeAdapter5.setTopAnimeList(it.data)
        }
    }

    // Upcoming Anime
    private fun prepareUpComingAnimeRecyclerView() {

        val animeAdapter6 = AnimeAdapter{
            val intoDetail = Intent(activity, DetailActivity::class.java)
            intoDetail.putExtra("id", it.mal_id.toString())
            startActivity(intoDetail)
        }

        binding.rvUpComingExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter6
        }

        viewModel.observeUpcomingAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter6.setTopAnimeList(it.data)
        }
    }



}