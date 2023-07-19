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

    private var myAnimeListLiveData = animeDatabase.animeDao().getAllData()


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

    fun observeUpcomingAnimeLiveData(): LiveData<Anime>{
        return upcomingAnimeLiveData
    }

    // My Anime List
    fun observeMyAnimeListLiveData(): LiveData<List<AnimeDataToSave>> {
        return myAnimeListLiveData
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