package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeplex.data.AnimeDetail
import com.example.animeplex.data.Characters
import com.example.animeplex.data.Manga
import com.example.animeplex.data.SimilarAnime
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MangaDetailViewModel(): ViewModel() {


    private var mangaDetailLiveData = MutableLiveData<Manga>()
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

}