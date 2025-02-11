package com.example.movieapp.ui.details

import androidx.core.os.requestProfiling
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Data.MovieDao
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Data.MovieRepository
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import kotlinx.coroutines.delay
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

            if(currentList.any{it.id == movie.id}){
                repository.bookmarkAMovie(movie,false)
                currentList.removeIf { it.id ==movie.id }
                _savedMovies.value = Resource.Success(currentList)
            }
            delay(300)
            getBookmarkedMovies()
        }
    }

    fun getBookmarkedMovies(){
        viewModelScope.launch {
            _savedMovies.value = Resource.Success(repository.getBookmarkedMovies().first().toList())
        }
    }

    fun isMovieSaved(movieId: Int):Boolean{
        return (_savedMovies.value as? Resource.Success)?.data?.any { it.id ==movieId }?: false
    }

    fun toggleBookmark(movie:MovieEntity){
        viewModelScope.launch {
            val isSaved = isMovieSaved(movie.id)
            if(isSaved)
            {
                removeMovie(movie)
            }
            else{
                saveMovie(movie)
            }
        }
    }
    //Fetching from API only if needed for Deeplink
    fun fetchMovieDetails(movieId:Int) {

        if (_movie.value is Resource.Success) return //skip fetching if movie already set

        viewModelScope.launch {
            _movie.value = Resource.Loading()
            try {
                val response = repository.getMovieDetails(movieId)
                _movie.value = response
            } catch (e: Exception) {
                _movie.value = Resource.Error("Failed to load movie details")
            }
        }
    }
    
}