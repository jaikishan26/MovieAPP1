package com.example.movieapp.Data

import com.example.movieapp.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor(){
    fun toMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.poster_path,
            releaseDate = movie.release_date,
            voteAverage = movie.vote_average,
            isBookmarked = false // by default
        )
    }

    fun toMovieEntityList(movies:List<Movie>): List<MovieEntity>{
        return movies.map { toMovieEntity(it) }
    }
}