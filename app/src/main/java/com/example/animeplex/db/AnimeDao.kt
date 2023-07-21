package com.example.animeplex.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animeplex.data.AnimeDataToSave

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(animeData: AnimeDataToSave)

    @Delete
    suspend fun delete(animeData: AnimeDataToSave)

    @Query("SELECT * FROM `Anime Data`")
    fun getAllData(): LiveData<List<AnimeDataToSave>>

    @Query("SELECT * FROM `Anime data` WHERE type = :type")
    fun getDataByType(type: String): LiveData<List<AnimeDataToSave>>
}