package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeplex.adapter.CharacterAnimeDetailAdapter
import com.example.animeplex.adapter.SimilarAnimeAdapter
import com.example.animeplex.adapter.TopAnimeAdapter
import com.example.animeplex.databinding.ActivityDetailBinding
import com.example.animeplex.viewmodel.AnimeDetailViewModel
import com.example.animeplex.viewmodel.CharactersViewModel
import com.example.animeplex.viewmodel.SimilarAnimeViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var id: String

    private var characterViewModel = CharactersViewModel()
    lateinit var characterAdapter: CharacterAnimeDetailAdapter

    private var similarAnimeViewModel = SimilarAnimeViewModel()
    lateinit var similarAnimeAdapter: SimilarAnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        id = intent.getStringExtra("id")!!

        viewModel = ViewModelProviders.of(this)[AnimeDetailViewModel::class.java]


        viewModel.getAnimeDetail(id.toInt())
        observeAnimeDetailLiveData()

        characterViewModel.getCharacters(id.toInt())
        prepareCharacterRecyclerView()
        observeCharacterLiveData()

        similarAnimeViewModel.getSimilarAnime(id.toInt())
        prepareSimilarAnimeRecyclerView()
        observeSimilarAnimeLiveData()
        onSimilarAnimeClick()

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

    // Characters
    private fun prepareCharacterRecyclerView() {
        characterAdapter = CharacterAnimeDetailAdapter()
        binding.rvCharactersDetails.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = characterAdapter
        }
    }

    private fun observeCharacterLiveData() {

        characterViewModel.observeCharacterLiveData().observe(this) {
            characterAdapter.setCharacterList(it.data)
        }
    }

    // Similar Anime
    private fun prepareSimilarAnimeRecyclerView() {
        similarAnimeAdapter = SimilarAnimeAdapter()
        binding.rvSimilarAnimeDetails.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAnimeAdapter
        }
    }

    private fun observeSimilarAnimeLiveData() {

        similarAnimeViewModel.observeSimilarAnimeLiveData().observe(this) {
            similarAnimeAdapter.setSimilarAnimeList(it.data)
        }
    }

    private fun onSimilarAnimeClick() {
        similarAnimeAdapter.onItemClick = {
            val inToDetails = Intent(this, DetailActivity::class.java)
            inToDetails.putExtra("id", it.entry.mal_id.toString())
            startActivity(inToDetails)
        }
    }
}