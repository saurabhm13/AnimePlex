package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.data.AnimeDetail
import com.example.animeplex.data.Characters
import com.example.animeplex.data.Manga
import com.example.animeplex.data.SimilarAnime
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeMangaDetailViewModel(
    private val animeDatabase: AnimeDatabase
): ViewModel() {


    private var animeDetailLiveData = MutableLiveData<AnimeDetail>()
    private var characterLiveData = MutableLiveData<Characters>()
    private var similarAnimeLiveData = MutableLiveData<SimilarAnime>()
    private var mangaDetailLiveData = MutableLiveData<Manga>()


    @OptIn(DelicateCoroutinesApi::class)
    fun getAnimeDetail(id: Int) {

        GlobalScope.launch(Dispatchers.IO){

            try {
                RetrofitInstance.api.getAnimeDetail(id).enqueue(object : Callback<AnimeDetail>{
                    override fun onResponse(call: Call<AnimeDetail>, response: Response<AnimeDetail>) {
                        if (response.body() != null){
                            animeDetailLiveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<AnimeDetail>, t: Throwable) {
                        Log.d("Detail Activity", t.message.toString())
                    }

                })
            }catch (e: Exception){
                Log.d("Detail Activity", e.message.toString())
            }
        }


    }

    fun observeAnimeDetailLiveData(): LiveData<AnimeDetail> {
        return animeDetailLiveData
    }

    // Characters
    @OptIn(DelicateCoroutinesApi::class)
    fun getCharacters(id: Int) {

        GlobalScope.launch(Dispatchers.IO){

            try {

                RetrofitInstance.api.getCharactersDetail(id).enqueue(object : Callback<Characters>{
                    override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                        if (response.body() != null){
                            characterLiveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Characters>, t: Throwable) {
                        Log.d("Character", t.message.toString())
                    }

                })

            }catch (e: Exception){
                Log.d("Character", e.message.toString())
            }
        }

    }

    fun observeCharacterLiveData(): LiveData<Characters>{
        return characterLiveData
    }

    // Similar Anime
    @OptIn(DelicateCoroutinesApi::class)
    fun getSimilarAnime(id: Int) {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RetrofitInstance.api.getSimilarAnime(id).enqueue(object : Callback<SimilarAnime>{
                    override fun onResponse(call: Call<SimilarAnime>, response: Response<SimilarAnime>) {
                        if (response.body() != null){
                            similarAnimeLiveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<SimilarAnime>, t: Throwable) {
                        Log.d("Similar Anime", t.message.toString())
                    }

                })

            }catch (e: Exception) {
                Log.d("Similar Anime", e.message.toString())
            }

        }
    }

    fun observeSimilarAnimeLiveData(): LiveData<SimilarAnime> {
        return similarAnimeLiveData
    }

    private var mangaCharacterLiveData = MutableLiveData<Characters>()
    private var similarMangaLiveData = MutableLiveData<SimilarAnime>()

    @OptIn(DelicateCoroutinesApi::class)
    fun getMangaDetail(id: Int) {

        GlobalScope.launch(Dispatchers.IO){

            try {
                RetrofitInstance.api.getMangaDetails(id).enqueue(object : Callback<Manga> {
                    override fun onResponse(call: Call<Manga>, response: Response<Manga>) {
                        if (response.body() != null){
                            mangaDetailLiveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Manga>, t: Throwable) {
                        Log.d("Detail Activity", t.message.toString())
                    }

                })
            }catch (e: Exception){
                Log.d("Detail Activity", e.message.toString())
            }
        }
    }

    fun observeMangaDetailLiveData(): LiveData<Manga> {
        return mangaDetailLiveData
    }

    // Add To List
    fun addAnimeToListFromAddIcon(animeData: AnimeDataToSave) {
        viewModelScope.launch {
            animeDatabase.animeDao().upsert(animeData)
        }
    }

}