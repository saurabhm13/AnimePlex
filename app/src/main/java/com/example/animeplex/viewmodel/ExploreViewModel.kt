package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeplex.data.Anime
import com.example.animeplex.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreViewModel(): ViewModel() {


    private var movieAnimeLiveData = MutableLiveData<Anime>()

    private var awardWinningAnimeLiveData = MutableLiveData<Anime>()
    private var actionAnimeLiveData = MutableLiveData<Anime>()
    private var animeByCategoryLiveData = MutableLiveData<Anime>()

    // Search Result
    private var animeSearchResultLiveData = MutableLiveData<Anime>()
    private var mangaSearchResultLiveData = MutableLiveData<Anime>()

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
}