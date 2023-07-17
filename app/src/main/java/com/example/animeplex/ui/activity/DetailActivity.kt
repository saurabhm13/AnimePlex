package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.animeplex.R
import com.example.animeplex.databinding.ActivityDetailBinding
import com.example.animeplex.viewmodel.AnimeDetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        id = intent.getStringExtra("id")!!

        viewModel = ViewModelProviders.of(this)[AnimeDetailViewModel::class.java]



        observeAnimeDetailLiveData()

        viewModel.getAnimeDetail(id.toInt())


    }

    @SuppressLint("SetTextI18n")
    private fun observeAnimeDetailLiveData() {

        viewModel.observeAnimeDetailLiveData().observe(this) {

            Glide.with(this)
                .load(it.data.images.jpg.large_image_url)
                .into(binding.imgAnime)

            binding.name.text = it.data.title_english
            val categorySize: Int = it.data.genres.size
            var i = 0
            var category: String

            while (i < categorySize){
                category = it.data.genres[i].name
                if (i+1 < categorySize){
                    binding.category.text = binding.category.text.toString() + category + " | "
                }else{
                    binding.category.text = binding.category.text.toString() + category
                }
                i++
            }

            binding.year.text = it.data.year.toString()
            binding.episode.text = it.data.episodes.toString()

            binding.duration.text = extractNumberFromString(it.data.duration).toString() + " min"
            binding.description.text = it.data.synopsis

        }
    }

    private fun extractNumberFromString(input: String): Int? {
        val regex = Regex("""\d+""")
        val matchResult = regex.find(input)
        return matchResult?.value?.toIntOrNull()
    }
}