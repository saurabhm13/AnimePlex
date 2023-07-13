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

class HomeViewModel(): ViewModel() {

    private var featuredAnimeLiveData = MutableLiveData<Anime>()

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

}