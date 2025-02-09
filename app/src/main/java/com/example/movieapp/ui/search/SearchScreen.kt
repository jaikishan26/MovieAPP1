package com.example.movieapp.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.Util.Resource
import com.example.movieapp.ui.components.MovieCard
import com.example.movieapp.ui.components.MovieItem

@Composable
fun SearchScreen(
    navController: NavController, viewModel: SearchViewModel
){
    //val viewModel: SearchViewModel = viewModel()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Log.d("SearchScreen", "Current Query: $searchQuery") // ✅ Debug Query in UI
    Log.d("SearchScreen", "Current Results: $searchResults") // ✅ Debug Search Results


    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ){
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {viewModel.updateSearchQuery(it)},
            label = { Text("Search for a movie. . . ") },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true,
            leadingIcon ={
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when(searchResults){
            is Resource.Loading ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Success -> {
                val movies = (searchResults as Resource.Success).data?: emptyList()
                Log.d("SearchViewModel", "Searching for: ${movies.size}") // ✅ Debug Query
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies){
                        movie ->
                        MovieCard(movie, navController)
//                        MovieItem(movie=movie, onClick = {
//                            navController.currentBackStackEntry?.savedStateHandle
//                                ?.set("selectedMovie", movie)
//                            navController.navigate(("details"))
//                        }
 //                       )
                    }
                }
            }

            is Resource.Error -> {
                (searchResults as Resource.Error).message?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}