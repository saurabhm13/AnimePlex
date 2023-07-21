package com.example.animeplex.db

import androidx.lifecycle.LiveData
import com.example.animeplex.data.AnimeDataToSave

class AnimeRepository(private val animeDao: AnimeDao) {

    suspend fun getAllData(): LiveData<List<AnimeDataToSave>> {
        return animeDao.getAllData()
    }

    suspend fun getDataByType(type: String): LiveData<List<AnimeDataToSave>> {
        return animeDao.getDataByType(type)
    }


}