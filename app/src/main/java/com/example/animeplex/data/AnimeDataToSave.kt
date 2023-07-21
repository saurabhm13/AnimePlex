package com.example.animeplex.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Anime Data")
data class AnimeDataToSave(
    @PrimaryKey
    var mal_id: Int,
    var title: String,
    var image: String,
    @ColumnInfo(name = "type")
    var type: String
)
