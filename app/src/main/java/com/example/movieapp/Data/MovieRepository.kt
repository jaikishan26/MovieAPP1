package com.example.movieapp.Data

import android.provider.SyncStateContract.Constants
import com.example.movieapp.ApiService
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton


// Fetching data from API and storing in DB and provinding functionality for accessing from database
// Acts as a central class for managing data from both API and local Database
@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieMapper,
    private val movieDao: MovieDao
) {   //apiservice to makeee requests to DB
                                            // MOviedao to inteact with local database

    //Getting Now Playing Movies

    suspend fun getNowPlayingMovies(page:Int): Resource<List<MovieEntity>>{
        // fetches the list of now playing movies from
        println("GET NIOW PLAYING CALLED")
        return try {
            val response = apiService.getNowPlayingMovies(Constant.API_KEY,page) //network call
            val movies = movieMapper.toMovieEntityList(response.results, page,"NowPlaying")
            movieDao.insertMovies(movies)
//            Resource.Success(movies)
            val list = movieDao.getAllMovies(page, "NowPlaying").first()
            Resource.Success(list)

            //Resource.Error( "Error Occurred")

        } catch (e: Exception){
            Resource.Error(e.message?:"Error Occurred")
        }
    }


    //Getting Trending Movies
    suspend fun getTrendingMovies(page:Int): Resource<List<MovieEntity>>{
        return try {
            val response = apiService.getTrendingMovies(Constant.API_KEY, page) //network call
            val movies = movieMapper.toMovieEntityList(response.results, page,"Trending")
            movieDao.insertMovies(movies)
            val list = movieDao.getAllMovies(page,"Trending").first()
            Resource.Success(list)
            //Resource.Success(movies)
        } catch (e: Exception){
            Resource.Error(e.message?:"Error Occurred")
        }
    }


    //Getting Bookmarked Movies
    fun getBookmarkedMovies():Flow<List<MovieEntity>>{
        return movieDao.getBookmarkedMovies()
    }

    //Bookmarking a Movie
    suspend fun bookmarkAMovie(movie:MovieEntity, isBookmarked:Boolean){
        val movie = movieDao.getMovieById(movie.id)
        movie?.let {
            //let used to handle nullable -- if movie null the let block is skipped
            val updatedMovie = it.copy(isBookmarked = isBookmarked)  // updating val
            movieDao.updateMovie(updatedMovie)
        }
    }
}