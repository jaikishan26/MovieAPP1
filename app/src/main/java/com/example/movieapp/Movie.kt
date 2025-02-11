package com.example.movieapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName



@Parcelize
data class Movie(
    val id: Int,
    val title:String = "",
    val overview: String = "",
    val poster_path: String = "",
    val release_date: String = "",
    val vote_average: Double
): Parcelable

data class MovieResponse(
    val results: List<Movie>
)
