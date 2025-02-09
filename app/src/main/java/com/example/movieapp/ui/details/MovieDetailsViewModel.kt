package com.example.movieapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import com.example.movieapp.movieService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel :ViewModel(){
    private val _movie = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movie: StateFlow<Resource<Movie>> get() = _movie

    private val _savedMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val savedMovies: StateFlow<Resource<List<Movie>>> get() = _savedMovies

    fun setMovieDetails(movie: Movie){
        _movie.value = Resource.Success(movie)
    }

    //Bookmark movie
    fun saveMovie(movie: Movie) {
        val currentList = (_savedMovies.value as? Resource.Success)?.data?.toMutableList()?: mutableListOf()

        if(!currentList.contains(movie)){
            currentList.add(movie)
            _savedMovies.value = Resource.Success(currentList)
        }
    }
//Remove movie from bookmark
    fun removeMovie(movie: Movie){
        val currentList = (_savedMovies.value as? Resource.Success)?.data?.toMutableList()?: mutableListOf()

        if(currentList.contains(movie)){
            currentList.remove(movie)
            _savedMovies.value = Resource.Success(currentList)
        }
    }
//TODO..........
    fun isMovieSaved(movieId: Int):Boolean{
        return (_savedMovies.value as? Resource.Success)?.data?.any { it.id ==movieId }?: false
    }
    //Fetching from API only if needed for Deeplink
    fun fetchMovieDetails(movieId:Int){

        if(_movie.value is Resource.Success) return //skip fetching if movie already set

        viewModelScope.launch {
            _movie.value = Resource.Loading()
            try {
                val response = movieService.getMovieDetails(movieId, Constant.API_KEY)
                _movie.value = Resource.Success(response)
            } catch (e:Exception){
                _movie.value = Resource.Error("Failed to load movie details")
            }

//            val response =
//            _nowPlayingMovies.value = Resource.Loading()
//            val response = movieService.getNowPlayingMovies(Constant.API_KEY)
//            _nowPlayingMovies.value = Resource.Success(response.results)
        }
    }
}