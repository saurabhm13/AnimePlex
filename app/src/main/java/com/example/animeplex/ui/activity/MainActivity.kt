package com.example.animeplex.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.animeplex.R
import com.example.animeplex.db.AnimeDatabase
import com.example.animeplex.viewmodel.HomeViewModel
import com.example.animeplex.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    val viewModel: HomeViewModel by lazy {
        val animeDatabase = AnimeDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(animeDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navController = Navigation.findNavController(this, R.id.host_fragment)

//        NavigationUI.setupWithNavController(bottomNavigation, navController)

        bottomNavigation.setupWithNavController(navController)
    }

    // Handle Up navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}