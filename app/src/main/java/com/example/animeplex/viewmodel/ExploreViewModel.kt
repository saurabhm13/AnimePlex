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

    fun getAnimeByCategory(genres: Int) {

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

    fun observeAnimeByCategoryLiveData(): LiveData<Anime> {
        return awardWinningAnimeLiveData
    }

}