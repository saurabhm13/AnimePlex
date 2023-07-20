package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeplex.adapter.AnimeAdapter
import com.example.animeplex.adapter.CharacterAnimeDetailAdapter
import com.example.animeplex.adapter.CharacterDetailAdapter
import com.example.animeplex.adapter.SimilarAnimeAdapter
import com.example.animeplex.databinding.ActivityCharactersSimilarAnimeBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.AnimeDetailViewModel

class CharactersSimilarAnimeActivity : AppCompatActivity() {

    lateinit var binding: ActivityCharactersSimilarAnimeBinding

    lateinit var animeDetailViewModel: AnimeDetailViewModel

    private lateinit var characterAdapter: CharacterDetailAdapter
    private lateinit var similarAnimeAdapter: SimilarAnimeAdapter

    lateinit var type: String
    lateinit var animeId: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersSimilarAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)
        animeDetailViewModel = AnimeDetailViewModel(animeDatabase)

        val intent = intent
        type = intent.getStringExtra("Type")!!
        animeId = intent.getStringExtra("Anime Id")!!



        when (type) {
            "Character" -> {
                animeDetailViewModel.getCharacters(animeId.toInt())
                prepareCharacterRecyclerView()

                binding.textTopBar.text = "Characters"
            }
            "Similar Anime" -> {
                animeDetailViewModel.getSimilarAnime(animeId.toInt())
                prepareSimilarAnimeRecycleView()
                onSimilarAnimeItemClick()

                binding.textTopBar.text = "More Like This"
            }
        }

    }

    private fun prepareCharacterRecyclerView() {

        characterAdapter = CharacterDetailAdapter()

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = characterAdapter
        }

        animeDetailViewModel.observeCharacterLiveData().observe(this) {
            characterAdapter.setCharacterList(it.data)
        }
    }

    // Similar Anime
    private fun prepareSimilarAnimeRecycleView() {

        similarAnimeAdapter = SimilarAnimeAdapter(20)

        binding.rvAnimeList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = similarAnimeAdapter
        }

        animeDetailViewModel.observeSimilarAnimeLiveData().observe(this) {
            similarAnimeAdapter.setSimilarAnimeList(it.data)
        }

    }

    private fun onSimilarAnimeItemClick() {
        similarAnimeAdapter.onItemClick = {
            val inToDetails = Intent(this, DetailActivity::class.java)
            inToDetails.putExtra("id", it.entry.mal_id)
            startActivity(inToDetails)
        }
    }
}