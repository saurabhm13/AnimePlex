package com.example.animeplex.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.animeplex.R
import com.example.animeplex.adapter.CategoryAdapter
import com.example.animeplex.data.CategoryHome
import com.example.animeplex.databinding.FragmentHomeBinding
import com.example.animeplex.ui.MainActivity
import com.example.animeplex.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFeaturedAnime()
        observeFeaturedAnime()

        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)
    }

    private fun createCategoryList() {

        categoryList.add(CategoryHome(R.drawable.action, "Action"))
        categoryList.add(CategoryHome(R.drawable.adv, "Adventure"))
        categoryList.add(CategoryHome(R.drawable.comedy, "Comedy"))
        categoryList.add(CategoryHome(R.drawable.drama, "Drama"))
        categoryList.add(CategoryHome(R.drawable.fantasy, "Fantasy"))
    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategoryHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeFeaturedAnime() {
        viewModel.observeFeaturedAnimeLiveData().observe(viewLifecycleOwner){
            val imageList = ArrayList<SlideModel>()
            imageList.add(SlideModel(it.data[0].images.jpg.image_url))
            imageList.add(SlideModel(it.data[1].images.jpg.image_url))
            imageList.add(SlideModel(it.data[2].images.jpg.image_url))

            binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        }
    }
}