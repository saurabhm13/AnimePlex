package com.example.animeplex.retrofit

import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime")
    fun getFeaturedAnime(
        @Query("limit") limit: String,
        @Query("status") status: String,
        @Query("order_by") orderBy: String,
        @Query("sort") sort: String
    ):Call<Anime>


    @GET("top/anime")
    fun getTopAnime(): Call<Anime>

}