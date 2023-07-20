package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeplex.adapter.CharacterAnimeDetailAdapter
import com.example.animeplex.adapter.SimilarAnimeAdapter
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.databinding.ActivityDetailBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.AnimeDetailViewModel
import com.example.animeplex.viewmodel.AnimeViewModelFactory
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

        val animeDatabase = AnimeDatabase.getInstance(this)
        val animeViewModelFactory = AnimeViewModelFactory(animeDatabase)

        viewModel = ViewModelProvider(this, animeViewModelFactory)[AnimeDetailViewModel::class.java]


        // Anime Details
        viewModel.getAnimeDetail(id.toInt())
        observeAnimeDetailLiveData()
        onDescriptionClick()

        // Characters
        viewModel.getCharacters(id.toInt())
        prepareCharacterRecyclerView()
        observeCharacterLiveData()
        onMoreCharacterClick()

        // Similar Anime
        viewModel.getSimilarAnime(id.toInt())
        prepareSimilarAnimeRecyclerView()
        observeSimilarAnimeLiveData()
        onSimilarAnimeClick()
        onMoreSimilarAnimeClick()

        // Add To List
        onAddToListClick()

    }

    private var animeToSave: AnimeDataToSave? = null
    private var mal_id: Int? = null
    private var title: String? = null
    private var image: String? = null


    @SuppressLint("SetTextI18n")
    private fun observeAnimeDetailLiveData() {

        viewModel.observeAnimeDetailLiveData().observe(this) {

            mal_id = it.data.mal_id
            title = it.data.title
            image = it.data.images.jpg.large_image_url

            animeToSave = AnimeDataToSave(mal_id!!, title!!, image!!)


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

            binding.duration.text = it.data.duration.let { it1 -> extractNumberFromString(it1).toString() } + " min"
            binding.description.text = it.data.synopsis
            binding.ratingBar.rating = it.data.score.toFloat()

        }
    }

    private fun onDescriptionClick() {

        binding.description.setOnClickListener {

            if (binding.description.maxLines == 8){
                binding.description.maxLines = Int.MAX_VALUE
            }else {
                binding.description.maxLines = 8
            }

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

        viewModel.observeCharacterLiveData().observe(this) {
            characterAdapter.setCharacterList(it.data)
        }
    }

    private fun onMoreCharacterClick() {
        binding.characterDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.characterMoreDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

    }

    // Similar Anime
    private fun prepareSimilarAnimeRecyclerView() {
        similarAnimeAdapter = SimilarAnimeAdapter(10)
        binding.rvSimilarAnimeDetails.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAnimeAdapter
        }
    }

    private fun observeSimilarAnimeLiveData() {

        viewModel.observeSimilarAnimeLiveData().observe(this) {
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

    private fun onMoreSimilarAnimeClick() {

        binding.similarAnimeDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.similarAnimeMoreDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }
    }

    // Add To List
    private fun onAddToListClick() {

//        animeToSave?.mal_id ?:
//
//        animeToSave.let {
//            viewModel.addAnimeToList(it!!)
//        }

        binding.addToListImg.setOnClickListener {
            animeToSave.let {
                viewModel.addAnimeToListFromAddIcon(it!!)
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

}