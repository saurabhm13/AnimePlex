package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeplex.adapter.CharacterAnimeDetailAdapter
import com.example.animeplex.adapter.SimilarAnimeAdapter
import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.databinding.ActivityMangaDetailBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.AnimeDetailViewModel
import com.example.animeplex.viewmodel.MangaDetailViewModel

class MangaDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMangaDetailBinding
    private var mangaDetailViewModel = MangaDetailViewModel()

    private lateinit var animeDetailViewModel: AnimeDetailViewModel
    private lateinit var characterAdapter: CharacterAnimeDetailAdapter
    private lateinit var similarAnimeAdapter: SimilarAnimeAdapter

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMangaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)
        animeDetailViewModel = AnimeDetailViewModel(animeDatabase)

        val intent = intent
        id = intent.getStringExtra("id")!!

        // Manga Details
        mangaDetailViewModel.getMangaDetail(id.toInt())
        observeMangaDetailRecyclerView()
        onDescriptionClick()

        // Characters
        animeDetailViewModel.getCharacters(id.toInt())
        prepareCharacterRecyclerView()
        observeCharacterLiveData()
        onMoreCharacterClick()

        // Similar Anime
        animeDetailViewModel.getSimilarAnime(id.toInt())
        prepareSimilarAnimeRecyclerView()
        observeSimilarAnimeLiveData()
        onSimilarMangaClick()
        onMoreSimilarMangaClick()

        // Add Manga to List
        onAddToListClick()

        // On Share Button Click
        onShareBtnClick()

    }

    private var mangaToSave: AnimeDataToSave? = null
    private var mal_id: Int? = null
    private var title: String? = null
    private var image: String? = null

    // Manga Details
    @SuppressLint("SetTextI18n")
    private fun observeMangaDetailRecyclerView() {

        mangaDetailViewModel.observeMangaDetailLiveData().observe(this) {

            mal_id = it.data.mal_id
            title = it.data.title_english
            image = it.data.images.jpg.image_url

            mangaToSave = AnimeDataToSave(mal_id!!, title!!, image!!, "Manga")

            Glide.with(this)
                .load(it.data.images.jpg.image_url)
                .into(binding.animeImageMangaDetail)

            if (title.equals(null)){
                binding.mangaNameMangaDetails.text = it.data.title
            }else {
                binding.mangaNameMangaDetails.text = it.data.title_english
            }


            binding.descriptionMangaDetails.text = it.data.synopsis

            val chapters = it.data.chapters.toString()
            val volumes = it.data.volumes.toString()

            val categorySize: Int = it.data.genres.size
            var i = 0
            var category: String

            while (i < categorySize){
                category = it.data.genres[i].name
                if (i+1 < categorySize){
                    binding.categoriesMangaDetails.text = binding.categoriesMangaDetails.text.toString() + category + " | "
                }else{
                    binding.categoriesMangaDetails.text = binding.categoriesMangaDetails.text.toString() + category
                }
                i++
            }

            if (chapters == "0") {
                binding.chapters.text = "-"
            }else {
                binding.chapters.text = it.data.chapters.toString()
            }

            if (volumes == "0") {
                binding.volumes.text = "-"
            }else {
                binding.volumes.text = it.data.volumes.toString()
            }

            binding.author.text = it.data.authors[0].name
        }
    }

    private fun onDescriptionClick() {

        binding.descriptionMangaDetails.setOnClickListener {

            if (binding.descriptionMangaDetails.maxLines == 10){
                binding.descriptionMangaDetails.maxLines = Int.MAX_VALUE
            }else {
                binding.descriptionMangaDetails.maxLines = 10
            }

        }
    }

    // Characters
    private fun prepareCharacterRecyclerView() {
        characterAdapter = CharacterAnimeDetailAdapter()
        binding.rvCharactersMangaDetails.apply {
            layoutManager = LinearLayoutManager(this@MangaDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = characterAdapter
        }
    }

    private fun observeCharacterLiveData() {
        animeDetailViewModel.observeCharacterLiveData().observe(this) {
            characterAdapter.setCharacterList(it.data)
        }
    }

    private fun onMoreCharacterClick() {
        binding.charactersMangaDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.charactersMoreMangaDetails.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }
    }

    // Similar Manga
    private fun prepareSimilarAnimeRecyclerView() {
        similarAnimeAdapter = SimilarAnimeAdapter(10)
        binding.rvSimilarMangaDetails.apply {
            layoutManager = LinearLayoutManager(this@MangaDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAnimeAdapter
        }
    }

    private fun observeSimilarAnimeLiveData() {
        animeDetailViewModel.observeSimilarAnimeLiveData().observe(this) {
            similarAnimeAdapter.setSimilarAnimeList(it.data)
        }
    }

    private fun onMoreSimilarMangaClick() {
        binding.similarMangaDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.similarMoreMangaDetails.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }
    }

    private fun onSimilarMangaClick() {
        similarAnimeAdapter.onItemClick = {
            val inToDetails = Intent(this, DetailActivity::class.java)
            inToDetails.putExtra("id", it.entry.mal_id.toString())
            startActivity(inToDetails)
        }
    }

    private fun onAddToListClick() {
        binding.addToListManga.setOnClickListener {
            mangaToSave.let {
                animeDetailViewModel.addAnimeToListFromAddIcon(it!!)
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onShareBtnClick() {
        binding.shareManga.setOnClickListener {
            val intoShare = Intent(Intent.ACTION_SEND)
            intoShare.type = "text/plain"
            intoShare.putExtra(Intent.EXTRA_SUBJECT, title)
            intoShare.putExtra(Intent.EXTRA_TEXT, title)
            val chooser = Intent.createChooser(intoShare, "Share Using..")
            startActivity(chooser)
        }
    }

}