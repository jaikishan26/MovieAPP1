package com.example.movieapp.Data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MovieEntity::class],
    version = 1,
    exportSchema = false)
abstract class MovieDatebase: RoomDatabase() {
    abstract fun movieDao():MovieDao
}