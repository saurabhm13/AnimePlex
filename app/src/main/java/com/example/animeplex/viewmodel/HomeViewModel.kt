package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val animeDatabase: AnimeDatabase
): ViewModel() {

    private var featuredAnimeLiveData = MutableLiveData<Anime>()
    private var topAnimeLiveData = MutableLiveData<Anime>()
    private var topMangaLiveData = MutableLiveData<Anime>()
    private var upcomingAnimeLiveData = MutableLiveData<Anime>()
    private var movieAnimeLiveData = MutableLiveData<Anime>()
    private var awardWinningAnimeLiveData = MutableLiveData<Anime>()
    private var actionAnimeLiveData = MutableLiveData<Anime>()

    // Search Result
    private var animeSearchResultLiveData = MutableLiveData<Anime>()
    private var mangaSearchResultLiveData = MutableLiveData<Anime>()

    private var animeByCategoryLiveData = MutableLiveData<Anime>()

    private var myAnimeListLiveData = animeDatabase.animeDao().getDataByType("Anime")
    private var myMangaListLiveData = animeDatabase.animeDao().getDataByType("Manga")
    private var allMyListLiveData = animeDatabase.animeDao().getAllData()


    // Featured Images
    fun getFeaturedAnime() {

        RetrofitInstance.api.getFeaturedAnime("3", "airing", "popularity", "asc").enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    featuredAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun observeFeaturedAnimeLiveData(): LiveData<Anime> {
        return featuredAnimeLiveData
    }

    // Top Anime
    fun getTopAnime() {

        RetrofitInstance.api.getTopAnime().enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    topAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun observeTopAnimeLiveData(): LiveData<Anime> {
        return topAnimeLiveData
    }

    // Top Manga
    fun getTopManga() {

        RetrofitInstance.api.getTopManga().enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    topMangaLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun observeTopMangaLiveData(): LiveData<Anime>{
        return topMangaLiveData
    }

    // Upcoming Anime
    fun getUpcomingAnime() {

        RetrofitInstance.api.getUpcomingAnime("airing", "popularity").enqueue(object : Callback<Anime> {
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    upcomingAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }

        })
    }

    // Anime Movie
    fun getMovieAnime() {

        RetrofitInstance.api.getMovieAnime("movie", "popularity").enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    movieAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Explore", t.message.toString())
            }

        })
    }

    fun observeMovieAnimeLiveData(): LiveData<Anime>{
        return movieAnimeLiveData
    }

    // Award wining Anime
    fun getAwardWinningAnime(genres: Int) {

        RetrofitInstance.api.getAnimeByCategory(genres).enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    awardWinningAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Award winning", t.message.toString())
            }

        })
    }

    fun observeAwardWinningAnimeLiveData(): LiveData<Anime> {
        return awardWinningAnimeLiveData
    }

    // Action Anime
    fun getActionAnime(genres: Int) {

        RetrofitInstance.api.getAnimeByCategory(genres).enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    actionAnimeLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Award winning", t.message.toString())
            }

        })
    }

    fun observeActionAnimeLiveData(): LiveData<Anime> {
        return actionAnimeLiveData
    }

    fun observeUpcomingAnimeLiveData(): LiveData<Anime>{
        return upcomingAnimeLiveData
    }

    // My Anime List
    fun observeMyAnimeListLiveData(): LiveData<List<AnimeDataToSave>> {
        return myAnimeListLiveData
    }

    fun observeMyMangaListLiveData(): LiveData<List<AnimeDataToSave>> {
        return myMangaListLiveData
    }

    fun observeAllMyListLiveData(): LiveData<List<AnimeDataToSave>> {
        return allMyListLiveData
    }

    // Search Result
    fun getAnimeSearchResult(animeSearch: String) {

        RetrofitInstance.api.getAnimeSearchResult(animeSearch).enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    animeSearchResultLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Anime search", t.message.toString())
            }

        })
    }

    fun observeAnimeSearchLiveData(): LiveData<Anime> {
        return animeSearchResultLiveData
    }

    fun getMangaSearchResult(animeSearch: String) {

        RetrofitInstance.api.getMangaSearchResult(animeSearch).enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    mangaSearchResultLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Anime search", t.message.toString())
            }

        })
    }

    fun observeMangaSearchLiveData(): LiveData<Anime> {
        return mangaSearchResultLiveData
    }

    // Anime by Category
    fun getAnimeByCategory(genres: Int) {

        RetrofitInstance.api.getAnimeByCategory(genres).enqueue(object : Callback<Anime>{
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.body() != null){
                    animeByCategoryLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                Log.d("Award winning", t.message.toString())
            }

        })

    }

    fun observeAnimeByCategoryLiveData(): LiveData<Anime> {
        return animeByCategoryLiveData
    }

    fun addAnimeToListFromUndo(animeData: AnimeDataToSave) {
        viewModelScope.launch {
            animeDatabase.animeDao().upsert(animeData)
        }
    }

    fun deleteAnimeFromList(animeData: AnimeDataToSave) {
        viewModelScope.launch {
            animeDatabase.animeDao().delete(animeData)
        }
    }
}