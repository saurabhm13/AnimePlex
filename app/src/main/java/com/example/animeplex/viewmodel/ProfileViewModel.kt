package com.example.animeplex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeplex.data.AnimeDataToSave
import com.example.animeplex.db.AnimeDatabase
import kotlinx.coroutines.launch

class ProfileViewModel(private val animeDatabase: AnimeDatabase): ViewModel() {


    private var myAnimeListLiveData = animeDatabase.animeDao().getAllData()

    fun addAnimeToListFromUndo(animeData: AnimeDataToSave) {
        viewModelScope.launch {
            animeDatabase.animeDao().upsert(animeData)
        }
    }

    fun deleteAnimeFromList(animeData: AnimeDataToSave) {
        viewModelScope.launch {
            animeDatabase.animeDao().delete(animeData)
        }
    }

    fun observeMyAnimeListLiveData(): LiveData<List<AnimeDataToSave>> {
        return myAnimeListLiveData
    }

}