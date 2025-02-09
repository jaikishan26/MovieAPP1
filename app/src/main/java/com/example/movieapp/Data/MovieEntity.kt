package com.example.movieapp.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie-table")
data class MovieEntity(

    @PrimaryKey val id:Int,
    @ColumnInfo(name = "Title")
    val title:String,
    @ColumnInfo(name = "Overview")
    val overview: String,
    @ColumnInfo(name = "posterPath")
    val posterPath: String,
    @ColumnInfo(name = "Release-Date")
    val releaseDate: String,
    @ColumnInfo(name = "Votes")
    val voteAverage: Double,
    @ColumnInfo(name = "isBookmarked")
    val isBookmarked: Boolean = false
)


