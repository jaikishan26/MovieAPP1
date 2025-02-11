package com.example.movieapp.ui.Saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.Util.Resource
import com.example.movieapp.ui.components.MovieCard
import com.example.movieapp.ui.details.MovieDetailsViewModel

@Composable
fun SavedMovieScreen(navController: NavController, viewModel: MovieDetailsViewModel){

    val savedMovies by viewModel.savedMovies.collectAsState()



    Column(modifier = Modifier
        .fillMaxSize().background(Color.Black)
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Saved Movies",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()).padding(8.dp),
            textAlign = TextAlign.Center,
            color = Color.White
            )

        when(savedMovies){
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Success -> {
                val movies = (savedMovies as Resource.Success).data
                if (movies != null) {
                    if(movies.isEmpty()) {
                        Text("No Movies saved yet!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize())
                    } else {

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(movies){
                                movie ->
                                MovieCard(movie, navController)
                            }
                        }
                    }
                }
            }

            is Resource.Error -> {
                (savedMovies as Resource.Error).message?.let {
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