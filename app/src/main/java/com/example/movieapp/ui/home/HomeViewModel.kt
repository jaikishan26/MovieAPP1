package com.example.movieapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ApiService

import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Data.MovieRepository
//import com.example.movieapp.DependencyInjection.DaggerAppComponent
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import com.example.movieapp.movieService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//this will fetch now playing and trending movies
class HomeViewModel(
    //private val repository: MovieRepository,
    //private val apiService: ApiService,
    //private val context: Context
): ViewModel(){
    //val apiService = (application as MyApplication).appComponent.provideApiService()
    //val apiService =  DaggerAppComponent.factory().create(context).provideApiService()


    //MutableStateFlow - special type of Flow in Kotlin that holds a mutable state,,,
    //It always holds the latest value and emits updates to collectors.
    //Resource.loading state that initial state if of loading
//    private val _nowPlayingMovies = MutableStateFlow<Resource<List<MovieEntity>>>(
//        Resource.Loading()
//    )
    private val _nowPlayingMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())


    //StateFlow is a read-only version of MutableStateFlow means it can't modify it
    //encapsulates _nowPlayingMovies

    //val nowPlayingMovies: StateFlow<Resource<List<MovieEntity>>> get() = _nowPlayingMovies

    val nowPlayingMovies: StateFlow<Resource<List<Movie>>> get() = _nowPlayingMovies

    private var nowPlayingPage = 1
    private val _isNowPlayingLoading = MutableStateFlow(false)
    val isNowPlayingLoading: StateFlow<Boolean> get() = _isNowPlayingLoading

    private val _trendingMovies = MutableStateFlow<Resource<List<Movie>>>(
        Resource.Loading())

    val trendingMovies: StateFlow<Resource<List<Movie>>> get() = _trendingMovies

    private var trendingPage = 1
    private val _isTrendingLoading = MutableStateFlow(false)
    val isTrendingLoading: StateFlow<Boolean> get() = _isTrendingLoading



    init {
        fetchNowPlayingMovies()
        fetchTrendingMovies()
    }

    // fetching  the list of now-playing movies and updates the _nowPlayingMovies StateFlow inside a ViewModel.
    //launch {} starts a new coroutine to run the function asynchronously.
    //viewModelScope is a coroutine scope bound to the lifecycle of the ViewModel.
    private fun fetchNowPlayingMovies(){
        viewModelScope.launch {
            _nowPlayingMovies.value = Resource.Loading()
            val response = movieService.getNowPlayingMovies(Constant.API_KEY,nowPlayingPage)
            _nowPlayingMovies.value = Resource.Success(response.results)

        //Before fetching movies, we set the value to Resource.Loading(), indicating that data is being loaded
            //Ensures that UI show loading indicator while waiting data
            //_nowPlayingMovies.value = repository.getNowPlayingMovies()
        }

    }

    private fun fetchTrendingMovies(){
        viewModelScope.launch {
            _trendingMovies.value = Resource.Loading()
            val response = movieService.getTrendingMovies(Constant.API_KEY, trendingPage)
            //_trendingMovies.value = repository.getTrendingMovies()
            _trendingMovies.value = Resource.Success(response.results)
        }
    }

    //////////////////////For Pagination//////////////////////////////

    fun loadMoreNowPlayingMovies(){
        if(_isNowPlayingLoading.value) return
        _isNowPlayingLoading.value = true

        viewModelScope.launch {
            try {
                val response = movieService.getNowPlayingMovies(Constant.API_KEY,nowPlayingPage+1) //Fetching Next Page
                val currentMovies = (_nowPlayingMovies.value as? Resource.Success)?.data.orEmpty()

                _nowPlayingMovies.value = Resource.Success(currentMovies + response.results)
                nowPlayingPage++
            } catch (e:Exception){
                _nowPlayingMovies.value = Resource.Error("Failed to load more Now Playing Movies")
            } finally {
                _isNowPlayingLoading.value = false
            }
        }
    }

    fun loadMoreTrendingMovies(){
        if(_isTrendingLoading.value) return
        _isTrendingLoading.value = true

        viewModelScope.launch {
            try {
                val response = movieService.getTrendingMovies(Constant.API_KEY,trendingPage+1) //Fetching Next Page
                val currentMovies = (_trendingMovies.value as? Resource.Success)?.data.orEmpty()

                _trendingMovies.value = Resource.Success(currentMovies + response.results)
                trendingPage++
            } catch (e:Exception){
                _trendingMovies.value = Resource.Error("Failed to load more Trending Movies")
            } finally {
                _isTrendingLoading.value = false
            }
        }
    }


}