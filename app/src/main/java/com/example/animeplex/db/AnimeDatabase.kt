package com.example.animeplex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animeplex.data.AnimeData
import com.example.animeplex.data.AnimeDataToSave

@Database(entities = [AnimeDataToSave::class], version = 1, exportSchema = false)
@TypeConverters(AnimeTypeConvertor::class)
abstract class AnimeDatabase: RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {

        @Volatile
        var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AnimeDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as AnimeDatabase
        }

    }
}