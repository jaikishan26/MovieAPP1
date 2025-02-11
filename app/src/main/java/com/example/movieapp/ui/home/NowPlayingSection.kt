package com.example.movieapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
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
fun NowPlayingSection(viewModel: HomeViewModel, navController: NavController){

    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val listState = rememberLazyListState()  // crreating a scroll state which tracks user's scroll position
    val isNowPlayingLoading by viewModel.isNowPlayingLoading.collectAsState()

    LaunchedEffect(listState) {   //runs API calls when list state changes
        snapshotFlow { //snapshot flow observes scroll changes in liststate dynamically
            //extract index of last visible item in list
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect{ // it collects scroll position updates, whenever user scrolls -> it receives the last visible item  index
            lastVisibleItemIndex ->
            val totalItems = listState.layoutInfo.totalItemsCount //gets total number of items currently loaded in lazyrow/colum
            if(lastVisibleItemIndex !=null && lastVisibleItemIndex >= totalItems -1){ //if last visible item is last in the list, trihger pagination
                viewModel.loadMoreNowPlayingMovies()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Now Playing",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(8.dp) )

        when(nowPlayingMovies){
            is Resource.Success -> {
                val movies = (nowPlayingMovies as Resource.Success).data?: emptyList()
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

                    item {
                        if(isNowPlayingLoading){
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .height(150.dp), // Ensure proper height
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator()
                            }
                        }
                    }
                }
            }

            is Resource.Error -> ErrorMessage(message = (nowPlayingMovies as Resource.Error).message)

            is Resource.Loading ->
            {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
            }
        }
    }

}