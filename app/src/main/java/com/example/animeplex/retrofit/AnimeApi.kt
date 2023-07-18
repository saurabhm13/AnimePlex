package com.example.animeplex.retrofit

import com.example.animeplex.data.Anime
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.AnimeDetail
import com.example.animeplex.data.Characters
import com.example.animeplex.data.SimilarAnime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("top/manga")
    fun getTopManga(): Call<Anime>

    @GET("anime")
    fun getUpcomingAnime(
        @Query("status") status: String,
        @Query("order_by") orderBy: String
    ): Call<Anime>

    @GET("anime/{id}")
    fun getAnimeDetail(
        @Path("id", encoded = true) id: Int
    ): Call<AnimeDetail>

    @GET("anime/{id}/characters")
    fun getCharactersDetail(
        @Path("id", encoded = true) id: Int
    ): Call<Characters>

    @GET("anime/{id}/recommendations")
    fun getSimilarAnime(
        @Path("id", encoded = true)id: Int
    ): Call<SimilarAnime>

    @GET("anime")
    fun getMovieAnime(
        @Query("type") type: String,
        @Query("order_by") orderBy: String
    ): Call<Anime>

    @GET("anime")
    fun getAnimeByCategory(
        @Query("genres") genres: Int
    ): Call<Anime>

}