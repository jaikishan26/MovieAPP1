package com.example.movieapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Data.MovieDao
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Data.MovieRepository
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import com.example.movieapp.movieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor
    (private val repository: MovieRepository,
            private val movieDao: MovieDao) :ViewModel()
{
    private val _movie = MutableStateFlow<Resource<MovieEntity>>(Resource.Loading())
    val movie: StateFlow<Resource<MovieEntity>> get() = _movie

    private val _savedMovies = MutableStateFlow<Resource<List<MovieEntity>>>(Resource.Success(emptyList()))
    val savedMovies: StateFlow<Resource<List<MovieEntity>>> get() = _savedMovies

    init {
        getBookmarkedMovies()
    }

    fun setMovieDetails(movie: MovieEntity){
        _movie.value = Resource.Success(movie)
    }

    //Bookmark movie
    fun saveMovie(movie: MovieEntity) {
        viewModelScope.launch {
            val currentList = (_savedMovies.value as? Resource.Success)?.data?.toMutableList()?: mutableListOf()

            if(!currentList.contains(movie)){
                currentList.add(movie)
                repository.bookmarkAMovie(movie, true)
                _savedMovies.value = Resource.Success(currentList)
            }
        }
    }
//Remove movie from bookmark
    fun removeMovie(movie: MovieEntity){
        viewModelScope.launch {
            val currentList = (_savedMovies.value as? Resource.Success)?.data?.toMutableList()?: mutableListOf()

            if(currentList.contains(movie)){
                currentList.remove(movie)
                repository.bookmarkAMovie(movie, false)
                _savedMovies.value = Resource.Success(currentList)
            }
        }
    }

    fun getBookmarkedMovies(){
        viewModelScope.launch {
            _savedMovies.value = Resource.Success(repository.getBookmarkedMovies().first())
        }
    }
//TODO..........
//    fun isMovieSaved(movieId: Int):Boolean{
//        return (_savedMovies.value as? Resource.Success)?.data?.any { it.id ==movieId }?: false
//    }

    suspend fun isMovieSaved(movieId:Int):Boolean{
        return movieDao.isMovieBookmarked(movieId)
    }

    fun toggleBookmark(movie:MovieEntity){
        viewModelScope.launch {
            if(movie.isBookmarked){
                removeMovie(movie)
            }
            else{
                saveMovie(movie)
            }
        }
    }
    //Fetching from API only if needed for Deeplink
    fun fetchMovieDetails(movieId:Int){

//        if(_movie.value is Resource.Success) return //skip fetching if movie already set
//
//        viewModelScope.launch {
//            _movie.value = Resource.Loading()
//            try {
//                //val response = movieService.getMovieDetails(movieId, Constant.API_KEY)
//                val response = repository.get
//                _movie.value = Resource.Success(response)
//            } catch (e:Exception){
//                _movie.value = Resource.Error("Failed to load movie details")
//            }

//            val response =
//            _nowPlayingMovies.value = Resource.Loading()
//            val response = movieService.getNowPlayingMovies(Constant.API_KEY)
//            _nowPlayingMovies.value = Resource.Success(response.results)
        }
    
}