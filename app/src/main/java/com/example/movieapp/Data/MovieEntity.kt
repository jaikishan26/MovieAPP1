package com.example.movieapp.Data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie-table")
data class MovieEntity(

    @PrimaryKey val id:Int,
    @ColumnInfo(name = "Title")
    val title:String,
    @ColumnInfo(name = "Overview")
    val overview: String,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "Release-Date")
    val releaseDate: String,
    @ColumnInfo(name = "Votes")
    val voteAverage: Double,
    @ColumnInfo(name = "page")
    val page:Int,
    @ColumnInfo(name = "isBookmarked")
    val isBookmarked: Boolean = false,
    @ColumnInfo(name = "movieType")
    val movieType:String

) : Parcelable


