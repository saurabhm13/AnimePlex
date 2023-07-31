package com.example.animeplex.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeplex.adapter.CharacterAdapter
import com.example.animeplex.adapter.SimilarAnimeAdapter
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.databinding.ActivityMangaDetailBinding
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.AnimeMangaDetailViewModel

class AnimeMangaDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMangaDetailBinding

    private lateinit var animeMangaDetailViewModel: AnimeMangaDetailViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var similarAnimeAdapter: SimilarAnimeAdapter

    lateinit var id: String
    lateinit var content: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMangaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeDatabase = AnimeDatabase.getInstance(this)
        animeMangaDetailViewModel = AnimeMangaDetailViewModel(animeDatabase)

        val intent = intent
        id = intent.getStringExtra("id")!!
        content = intent.getStringExtra("Content")!!

        // Manga Details

        observeAnimeMangaDetailLiveData()
        onDescriptionClick()

        // Characters
        animeMangaDetailViewModel.getCharacters(id.toInt())
        prepareCharacterRecyclerView()
        observeCharacterLiveData()
        onMoreCharacterClick()

        // Similar Anime
        animeMangaDetailViewModel.getSimilarAnime(id.toInt())
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
    private fun observeAnimeMangaDetailLiveData() {

        if (content == "Manga") {

            animeMangaDetailViewModel.getMangaDetail(id.toInt())

            animeMangaDetailViewModel.observeMangaDetailLiveData().observe(this) {

                // Save
                mal_id = it.data.mal_id
                title = it.data.title_english
                image = it.data.images.jpg.image_url

                mangaToSave = AnimeDataToSave(mal_id!!, title!!, image!!, "Manga")

                // Image
                Glide.with(this)
                    .load(it.data.images.jpg.image_url)
                    .into(binding.imageAnimeMangaDetail)

                // Title null check
                if (title.equals(null)){
                    binding.nameAnimeMangaDetails.text = it.data.title
                }else {
                    binding.nameAnimeMangaDetails.text = it.data.title_english
                }

                // Description
                binding.descriptionDetails.text = it.data.synopsis

                // Categories
                val categorySize: Int = it.data.genres.size
                var i = 0
                var category: String

                while (i < categorySize){
                    category = it.data.genres[i].name
                    if (i+1 < categorySize){
                        binding.categoriesDetail.text = binding.categoriesDetail.text.toString() + category + " | "
                    }else{
                        binding.categoriesDetail.text = binding.categoriesDetail.text.toString() + category
                    }
                    i++
                }

                binding.txtYearChapterDetail.text = "Chapter"
                binding.txtEpisodeAuthor.text = "Author"
                binding.txtDurationVolume.text = "Volume"

                val chapters = it.data.chapters.toString()
                val volumes = it.data.volumes.toString()

                // Chapters
                if (chapters == "0") {
                    binding.yearChapters.text = "-"
                }else {
                    binding.yearChapters.text = it.data.chapters.toString()
                }

                // Volumes
                if (volumes == "0") {
                    binding.durationVolumes.text = "-"
                }else {
                    binding.durationVolumes.text = it.data.volumes.toString()
                }

                // Author
                binding.episodeAuthor.text = it.data.authors[0].name

            }

        }else if (content == "Anime") {

            animeMangaDetailViewModel.getAnimeDetail(id.toInt())

            animeMangaDetailViewModel.observeAnimeDetailLiveData().observe(this) {

                // Save
                mal_id = it.data.mal_id
                title = it.data.title_english
                image = it.data.images.jpg.image_url

                mangaToSave = AnimeDataToSave(mal_id!!, title!!, image!!, "Anime")

                // Image
                Glide.with(this)
                    .load(it.data.images.jpg.image_url)
                    .into(binding.imageAnimeMangaDetail)

                // Title null check
                if (title.equals(null)){
                    binding.nameAnimeMangaDetails.text = it.data.title
                }else {
                    binding.nameAnimeMangaDetails.text = it.data.title_english
                }

                // Description
                binding.descriptionDetails.text = it.data.synopsis

                // Categories
                val categorySize: Int = it.data.genres.size
                var i = 0
                var category: String

                while (i < categorySize){
                    category = it.data.genres[i].name
                    if (i+1 < categorySize){
                        binding.categoriesDetail.text = binding.categoriesDetail.text.toString() + category + " | "
                    }else{
                        binding.categoriesDetail.text = binding.categoriesDetail.text.toString() + category
                    }
                    i++
                }

                binding.txtYearChapterDetail.text = "Year"
                binding.txtEpisodeAuthor.text = "Episode"
                binding.txtDurationVolume.text = "Duration"

                val year = it.data.year
                val episode = it.data.episodes

                // Year
                if (year.equals(null) || year == 0) {
                    binding.yearChapters.text = "-"
                }else {
                    binding.yearChapters.text = year.toString()
                }

                // Episode
                if (episode.equals(null) || episode == 0) {
                    binding.episodeAuthor.text = "-"
                }else {
                    binding.episodeAuthor.text = episode.toString()
                }

                // Duration
                binding.durationVolumes.text = it.data.duration
            }
        }else {
            Toast.makeText(this, "Invalid Data", Toast.LENGTH_LONG).show()
        }

    }

    private fun onDescriptionClick() {

        binding.descriptionDetails.setOnClickListener {

            if (binding.descriptionDetails.maxLines == 10){
                binding.descriptionDetails.maxLines = Int.MAX_VALUE
            }else {
                binding.descriptionDetails.maxLines = 10
            }

        }
    }

    // Characters
    private fun prepareCharacterRecyclerView() {
        characterAdapter = CharacterAdapter()
        binding.rvCharactersDetails.apply {
            layoutManager = LinearLayoutManager(this@AnimeMangaDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = characterAdapter
        }
    }

    private fun observeCharacterLiveData() {
        animeMangaDetailViewModel.observeCharacterLiveData().observe(this) {
            characterAdapter.setCharacterList(it.data)
        }
    }

    private fun onMoreCharacterClick() {
        binding.charactersDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.charactersMoreDetails.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Character")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }
    }

    // Similar Manga
    private fun prepareSimilarAnimeRecyclerView() {
        similarAnimeAdapter = SimilarAnimeAdapter(10)
        binding.rvSimilarDetails.apply {
            layoutManager = LinearLayoutManager(this@AnimeMangaDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAnimeAdapter
        }
    }

    private fun observeSimilarAnimeLiveData() {
        animeMangaDetailViewModel.observeSimilarAnimeLiveData().observe(this) {
            similarAnimeAdapter.setSimilarAnimeList(it.data)
        }
    }

    private fun onMoreSimilarMangaClick() {
        binding.similarDetail.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }

        binding.similarMoreDetails.setOnClickListener {
            val intoCharacterSimilarAnime = Intent(this, CharactersSimilarAnimeActivity::class.java)
            intoCharacterSimilarAnime.putExtra("Type", "Similar Anime")
            intoCharacterSimilarAnime.putExtra("Anime Id", mal_id.toString())
            startActivity(intoCharacterSimilarAnime)
        }
    }

    private fun onSimilarMangaClick() {
        similarAnimeAdapter.onItemClick = {
            val inToDetails = Intent(this, AnimeMangaDetailActivity::class.java)
            inToDetails.putExtra("id", it.entry.mal_id.toString())
            inToDetails.putExtra("Content", "Anime")
            startActivity(inToDetails)
        }
    }

    private fun onAddToListClick() {
        binding.addToListDetail.setOnClickListener {
            mangaToSave.let {
                animeMangaDetailViewModel.addAnimeToListFromAddIcon(it!!)
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onShareBtnClick() {
        binding.shareDetail.setOnClickListener {
            val intoShare = Intent(Intent.ACTION_SEND)
            intoShare.type = "text/plain"
            intoShare.putExtra(Intent.EXTRA_SUBJECT, title)
            intoShare.putExtra(Intent.EXTRA_TEXT, title)
            val chooser = Intent.createChooser(intoShare, "Share Using..")
            startActivity(chooser)
        }
    }

}