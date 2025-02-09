package com.example.movieapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.Util.Resource
import com.example.movieapp.ui.components.ErrorMessage
import com.example.movieapp.ui.components.LoadingIndicator
import com.example.movieapp.ui.components.MovieItem

@Composable
fun TrendingSection(viewModel: HomeViewModel, navController: NavController){

    val trendingMovies by viewModel.trendingMovies.collectAsState()
    val listState = rememberLazyListState()
    val isTrendingLoading by viewModel.isTrendingLoading.collectAsState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItems - 1) {
                    viewModel.loadMoreTrendingMovies()
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Now Playing",
            //style = MaterialTheme.typography.titleLarge,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(8.dp) )

        when(trendingMovies){
            is Resource.Success -> {
                val movies = (trendingMovies as Resource.Success).data?: emptyList()
                LazyRow (state = listState,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp)){
                    items(movies){
                            movie ->
                        MovieItem(movie=movie, onClick = {
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("selectedMovie", movie)
                            navController.navigate("details/${movie.id}")
                        })
                    }

                    ///TODO .....
                    item {
                        if(isTrendingLoading){
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }

            is Resource.Error -> ErrorMessage(message = (trendingMovies as Resource.Error).message)

            is Resource.Loading -> LoadingIndicator()
        }
    }

}