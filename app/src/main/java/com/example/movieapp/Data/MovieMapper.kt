package com.example.movieapp.Data

import com.example.movieapp.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor(){
    fun toMovieEntity(movie: Movie, page:Int, movieType:String): MovieEntity {
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.poster_path ?: "",
            //posterPath = movie.poster_path,
            releaseDate = movie.release_date,
            voteAverage = movie.vote_average,
            page = page,
            movieType = movieType,
            isBookmarked = false // by default
        )
    }

    fun toMovieEntityList(movies:List<Movie>, page:Int, movieType:String): List<MovieEntity>{
        return movies.map { toMovieEntity(it,page,movieType ) }
    }
}