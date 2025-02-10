package com.example.movieapp.ui.components

import android.content.Context
import android.content.Intent
import com.example.movieapp.Data.MovieEntity

fun shareMovie(context: Context, movie:MovieEntity){
    val movieUrl = "https://www.themoviedb.org/movie/${movie.id}"
    val shareText = "Check out this movie: ${movie.title}  \n\n$movieUrl"

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share ${movie.title}")
    context.startActivity(shareIntent)
}