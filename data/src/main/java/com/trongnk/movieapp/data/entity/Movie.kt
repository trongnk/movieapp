package com.trongnk.movieapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "rating") val rating: Float,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "trailerLink") val trailerLink: String,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "watchList") var watchList: Boolean = false
)
