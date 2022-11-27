package com.trongnk.movieapp.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trongnk.movieapp.data.entity.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
