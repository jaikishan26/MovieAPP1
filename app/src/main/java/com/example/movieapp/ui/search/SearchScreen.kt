package com.example.movieapp.ui.search

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController, viewModel: SearchViewModel
){

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Log.d("SearchScreen", "Current Query: $searchQuery")
    Log.d("SearchScreen", "Current Results: $searchResults")


    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ){
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {viewModel.updateSearchQuery(it)},
            label = { Text("Search for a movie. . . ", color = Color.White) },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true,
            leadingIcon ={
                Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White)
            },
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray
            )
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