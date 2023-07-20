package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeplex.data.SimilarAnime
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarAnimeViewModel(): ViewModel() {


//    private var similarAnimeLiveData = MutableLiveData<SimilarAnime>()

//    @OptIn(DelicateCoroutinesApi::class)
//    fun getSimilarAnime(id: Int) {
//
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                RetrofitInstance.api.getSimilarAnime(id).enqueue(object : Callback<SimilarAnime>{
//                    override fun onResponse(call: Call<SimilarAnime>, response: Response<SimilarAnime>) {
//                        if (response.body() != null){
//                            similarAnimeLiveData.value = response.body()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<SimilarAnime>, t: Throwable) {
//                        Log.d("Similar Anime", t.message.toString())
//                    }
//
//                })
//
//            }catch (e: Exception) {
//                Log.d("Similar Anime", e.message.toString())
//            }
//
//        }
//    }
//
//    fun observeSimilarAnimeLiveData(): LiveData<SimilarAnime> {
//        return similarAnimeLiveData
//    }

}