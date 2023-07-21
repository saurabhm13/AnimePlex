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
import com.example.animeplex.ui.activity.AllCategoryActivity
import com.example.animeplex.ui.activity.AnimeListActivity
import com.example.animeplex.ui.activity.AnimeListByCategoryActivity
import com.example.animeplex.ui.activity.DetailActivity
import com.example.animeplex.ui.activity.MainActivity
import com.example.animeplex.ui.activity.MangaDetailActivity
import com.example.animeplex.viewmodel.ExploreViewModel
import com.example.animeplex.viewmodel.HomeViewModel

class ExploreFragment : Fragment() {


    lateinit var binding: FragmentExploreBinding
    lateinit var viewModel: HomeViewModel

    private var exploreViewModel = ExploreViewModel()

    lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = ArrayList<CategoryHome>()



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
        onMoreMovieAnimeClick()

        // Category
        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
        onCategoryItemClick()
        onMoreCategoryClick()

        // Top Manga
        viewModel.getTopManga()
        prepareTopMangaRecyclerView()
        onMoreTopMangaClick()

        // Using delay of 2 sec for Upcoming anime because api handles 3 request/sec
        val handler = Handler()

        handler.postDelayed(
            Runnable {

                // Award winning
                exploreViewModel.getAwardWinningAnime(46)
                prepareAwardWinningAnimeRecyclerView()
                onMoreAwardWinningClick()

                // Top Anime
                prepareTopAnimeRecyclerView()
                onMoreTopAnimeClick()

            },2000
        )

        handler.postDelayed(
            Runnable {

                // Action Anime
                exploreViewModel.getActionAnime(1)
                prepareActionAnimeRecyclerView()
                onMoreActionAnimeClick()

                // UpComing anime
                viewModel.getUpcomingAnime()
                prepareUpComingAnimeRecyclerView()
                onMoreUpComingAnimeClick()

            }, 2000
        )


    }

    // Movie Anime
    private fun prepareMovieAnimeRecyclerView() {

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

    private fun onMoreMovieAnimeClick() {
        binding.animeMovieExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Anime Movie")
            startActivity(intoAnimeList)
        }

        binding.animeMovieMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "AnimeMovie")
            startActivity(intoAnimeList)
        }
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
            categoryAdapter.onItemClick = {
                val intoAnimeByCategory = Intent(activity, AnimeListByCategoryActivity::class.java)
                intoAnimeByCategory.putExtra("Category Id", it.id.toString())
                intoAnimeByCategory.putExtra("Category Name", it.name)
                startActivity(intoAnimeByCategory)
            }
        }
    }

    private fun onMoreCategoryClick() {
        binding.categoriesExplore.setOnClickListener {
            val intoAllCategory = Intent(activity, AllCategoryActivity::class.java)
            startActivity(intoAllCategory)
        }

        binding.categoryMoreExplore.setOnClickListener {
            val intoAllCategory = Intent(activity, AllCategoryActivity::class.java)
            startActivity(intoAllCategory)
        }
    }

    // Top Manga
    private fun prepareTopMangaRecyclerView() {

        val animeAdapter2 = AnimeAdapter {
            val inToMangaDetails = Intent(activity, MangaDetailActivity::class.java)
            inToMangaDetails.putExtra("id", it.mal_id.toString())
            startActivity(inToMangaDetails)
        }

        binding.rvTopMangaExplore.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animeAdapter2
        }

        viewModel.observeTopMangaLiveData().observe(viewLifecycleOwner) {
            animeAdapter2.setTopAnimeList(it.data)
        }
    }

    private fun onMoreTopMangaClick() {
        binding.topMangaExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Manga")
            startActivity(intoAnimeList)
        }

        binding.topMangaMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Manga")
            startActivity(intoAnimeList)
        }
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

    private fun onMoreTopAnimeClick() {
        binding.topAnimeExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Anime")
            startActivity(intoAnimeList)
        }

        binding.topAnimeMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Top Anime")
            startActivity(intoAnimeList)
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

        exploreViewModel.observeAwardWinningAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter4.setTopAnimeList(it.data)
        }
    }

    private fun onMoreAwardWinningClick() {
        binding.awardWinningAnimeExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Award Winning")
            startActivity(intoAnimeList)
        }

        binding.awardWinningAnimeMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Award Winning")
            startActivity(intoAnimeList)
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

        exploreViewModel.observeActionAnimeLiveData().observe(viewLifecycleOwner) {
            animeAdapter5.setTopAnimeList(it.data)
        }
    }

    private fun onMoreActionAnimeClick() {
        binding.actionAnimeExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Action Anime")
            startActivity(intoAnimeList)
        }

        binding.actionAnimeMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Action Anime")
            startActivity(intoAnimeList)
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

    private fun onMoreUpComingAnimeClick() {
        binding.upComingExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Upcoming Anime")
            startActivity(intoAnimeList)
        }

        binding.upComingMoreExplore.setOnClickListener {
            val intoAnimeList = Intent(activity, AnimeListActivity::class.java)
            intoAnimeList.putExtra("Type", "Upcoming Anime")
            startActivity(intoAnimeList)
        }
    }


}