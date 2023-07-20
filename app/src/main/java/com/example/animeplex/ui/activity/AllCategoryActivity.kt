package com.example.animeplex.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeplex.R
import com.example.animeplex.adapter.CategoryAdapter
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.ActivityAllCategoryBinding

class AllCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAllCategoryBinding

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = ArrayList<CategoryHome>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Category List
        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
        onCategoryItemClick()
    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvAllCategory.apply {
            layoutManager = GridLayoutManager(this@AllCategoryActivity, 2, LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun createCategoryList() {

        categoryList.add(CategoryHome(1, R.drawable.action, "Action"))
        categoryList.add(CategoryHome(2, R.drawable.adv, "Adventure"))
        categoryList.add(CategoryHome(5, R.drawable.avant_garde, "Avant Garde"))
        categoryList.add(CategoryHome(46, R.drawable.award_winning, "Award Winning"))
        categoryList.add(CategoryHome(4, R.drawable.comedy, "Comedy"))
        categoryList.add(CategoryHome(39, R.drawable.detective, "Detective"))
        categoryList.add(CategoryHome(8, R.drawable.drama, "Drama"))
        categoryList.add(CategoryHome(13, R.drawable.historical, "Historical"))
        categoryList.add(CategoryHome(14, R.drawable.horror, "Horror"))
        categoryList.add(CategoryHome(15, R.drawable.kids, "Kids"))
        categoryList.add(CategoryHome(64, R.drawable.love_polygon, "Love Polygon"))
        categoryList.add(CategoryHome(17, R.drawable.martial_arts, "Martial Art"))
        categoryList.add(CategoryHome(67, R.drawable.medical, "Medical"))
        categoryList.add(CategoryHome(38, R.drawable.military, "Military"))
        categoryList.add(CategoryHome(19, R.drawable.music, "Music"))
        categoryList.add(CategoryHome(7, R.drawable.mystery, "Mystery"))
        categoryList.add(CategoryHome(6, R.drawable.mystery, "Mythology"))
        categoryList.add(CategoryHome(10, R.drawable.organized_crime, "Organized Crime"))
        categoryList.add(CategoryHome(70, R.drawable.performing_arts, "Performing Art"))
        categoryList.add(CategoryHome(40, R.drawable.psychological, "Psychological"))
        categoryList.add(CategoryHome(3, R.drawable.racing, "Racing"))
        categoryList.add(CategoryHome(72, R.drawable.reincarnation, "Reincarnation"))
        categoryList.add(CategoryHome(73, R.drawable.reverse_harem, "Reverse Harem"))
        categoryList.add(CategoryHome(22, R.drawable.romance, "Romance"))
        categoryList.add(CategoryHome(21, R.drawable.samurai, "Samurai"))
        categoryList.add(CategoryHome(21, R.drawable.school, "School"))
        categoryList.add(CategoryHome(24, R.drawable.sci_fi, "Sci-Fi"))
        categoryList.add(CategoryHome(36, R.drawable.slice_of_life, "Slice Of Life"))
        categoryList.add(CategoryHome(29, R.drawable.space, "Space"))
        categoryList.add(CategoryHome(30, R.drawable.space, "Sports"))
        categoryList.add(CategoryHome(11, R.drawable.strategy_game, "Strategy Game"))
        categoryList.add(CategoryHome(31, R.drawable.super_power, "Super Power"))
        categoryList.add(CategoryHome(37, R.drawable.supernatural, "Supernatural"))
        categoryList.add(CategoryHome(76, R.drawable.survival, "Survival"))
        categoryList.add(CategoryHome(41, R.drawable.suspense, "Suspense"))
        categoryList.add(CategoryHome(77, R.drawable.team_sports, "Team Sports"))
        categoryList.add(CategoryHome(78, R.drawable.time_travel, "Time Travel"))
        categoryList.add(CategoryHome(32, R.drawable.vampire, "Vampire"))
        categoryList.add(CategoryHome(79, R.drawable.video_game, "Video Games"))
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = {
            val intoAnimeByCategory = Intent(this, AnimeListByCategoryActivity::class.java)
            intoAnimeByCategory.putExtra("Category Id", it.id.toString())
            intoAnimeByCategory.putExtra("Category Name", it.name)
            startActivity(intoAnimeByCategory)
        }
    }
}