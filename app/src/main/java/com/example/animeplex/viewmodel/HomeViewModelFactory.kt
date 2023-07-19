package com.example.animeplex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animeplex.db.AnimeDatabase

class HomeViewModelFactory(
    private val animeDatabase: AnimeDatabase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(animeDatabase) as T
    }

}