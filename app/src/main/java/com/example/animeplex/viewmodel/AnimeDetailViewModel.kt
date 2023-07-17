package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.AnimeDetail
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeDetailViewModel(): ViewModel() {


    private var animeDetailLiveData = MutableLiveData<AnimeDetail>()


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

//        RetrofitInstance.api.getAnimeDetail(21).enqueue(object : Callback<AnimeData>{
//            override fun onResponse(call: Call<AnimeData>, response: Response<AnimeData>) {
//                if (response.body() != null){
//                    animeDetailLiveData.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<AnimeData>, t: Throwable) {
//                Log.d("Detail Activity", t.message.toString())
//            }
//
//        })
    }

    fun observeAnimeDetailLiveData(): LiveData<AnimeDetail> {
        return animeDetailLiveData
    }

}