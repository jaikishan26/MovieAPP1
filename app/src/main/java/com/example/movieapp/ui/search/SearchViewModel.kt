package com.example.movieapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.room.util.query
import com.example.movieapp.ApiService
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Data.MovieMapper
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import com.example.movieapp.movieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieMapper): ViewModel()
{

    private val _searchResults = MutableStateFlow<Resource<List<MovieEntity>>>(Resource.Loading())

    val searchResults: StateFlow<Resource<List<MovieEntity>>> get() = _searchResults

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    init{
        observeSearchQuery()
    }

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    private fun observeSearchQuery(){
        _searchQuery.debounce(500)//wait 5sec after user stops typins
            .distinctUntilChanged() // ignoring duplicate queries
            .filter { it.length>2 } // search only for 3 more size stroing
            .onEach { query ->
                searchMovies(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchMovies(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            _searchResults.value = Resource.Loading()
            Log.d("SearchViewModel", "Searching for: $query") // ✅ Debug Query

            try{
               // val response = movieService.searchMovies(query, Constant.API_KEY)
//                Log.d("SearchViewModel", "Total Results: ${response.total_results}")
//                Log.d("SearchViewModel", "Total Pages: ${response.total_pages}")
//                Log.d("SearchViewModel", "Movies Count: ${response.results.size}")
                val response = apiService.searchMovies(query, Constant.API_KEY)
                val searchMovies = movieMapper.toMovieEntityList(response.results,0,"search" )
                _searchResults.value = Resource.Success(searchMovies)
                //Log.d("SearchViewModel", "API Response: ${response.results}")
            } catch(e:Exception){
                //Log.e("SearchViewModel", "Error: ${e.message}") // ✅ Debug Errors
                _searchResults.value = Resource.Error("Error: ${e.message}")

            }
        }
    }
}