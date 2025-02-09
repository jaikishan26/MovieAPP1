package com.example.movieapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.Util.Resource
import com.example.movieapp.Util.Screen
import com.example.movieapp.ui.components.ErrorMessage
import com.example.movieapp.ui.components.FeaturedMovieBanner
import com.example.movieapp.ui.components.LoadingIndicator
import com.example.movieapp.ui.components.MovieItem
import com.example.movieapp.ui.components.SectionHeader

//Displays now playing and Trending movies
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

   val viewModel: HomeViewModel = viewModel()

    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val trendingMovies by viewModel.trendingMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.netflix),
                            contentDescription = "Netflix Logo",
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text ="Movie App",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ))
                    }
                },
                actions = {
                    IconButton(onClick = {
                    navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            BottomAppBar(
                    modifier = Modifier.padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    ),
                containerColor = Color.Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {/* TODO navigate to home*/ }) {
                        //Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                        Icon(painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp))
                    }
                    // Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        navController.navigate("saved")
                    }) {
                        Icon(painter = painterResource(id = R.drawable.bookmark),
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp))
                    }
                    // Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {/* TODO Implement profile navigation */}) {
                        Icon(painter = painterResource(id = R.drawable.user),
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp))
                    }
                }

            }
        },
        content = {
            padding->
            LazyColumn(
                modifier = Modifier.padding(padding)
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // MOvie Banner for 1st movie
                item {
                    nowPlayingMovies.let {resource ->
                        if(resource is Resource.Success && !resource.data.isNullOrEmpty()){
                            FeaturedMovieBanner(movie = resource.data.first(),navController)
                        }
                    }
                }

                //Now Playing Section of home screenn
                item{
                    NowPlayingSection(viewModel, navController)
//                    Text(text = "Now Playing",
//                        //style = MaterialTheme.typography.titleLarge,
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        ),
//                        modifier = Modifier.padding(8.dp) )
//
//                    when(nowPlayingMovies){
//                        is Resource.Success -> {
//                            val movies = (nowPlayingMovies as Resource.Success).data?: emptyList()
//                            LazyRow (horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                modifier = Modifier.padding(horizontal = 8.dp)){
//                                items(movies){
//                                        movie ->
//                                    MovieItem(movie=movie, onClick = {
//                                        navController.currentBackStackEntry
//                                            ?.savedStateHandle
//                                            ?.set("selectedMovie", movie)
//                                        navController.navigate("details")
//                                    })
//                                }
//                            }
//                        }
//
//                        is Resource.Error -> ErrorMessage(message = (nowPlayingMovies as Resource.Error).message)
//
//                        is Resource.Loading -> LoadingIndicator()
//                    }
                }

                //Trending Section
                item {
                    TrendingSection(viewModel, navController)
//                    Text(text = "Trending",
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        ),
//                        modifier = Modifier.padding(8.dp) )
//                    when(trendingMovies){
//                        is Resource.Success -> {
//                            val movies = (trendingMovies as Resource.Success).data?: emptyList()
//                            LazyRow (horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                modifier = Modifier.padding(horizontal = 8.dp)) {
//                                items(movies){
//                                        movie ->
//                                    MovieItem(movie=movie, onClick = {
//                                        navController.currentBackStackEntry
//                                            ?.savedStateHandle
//                                            ?.set("selectedMovie", movie)
//                                        navController.navigate("details")
//                                        // TODO navController.navigate("details/${movie.id}")
//                                    })
//                                }
//                            }
//                        }
//
//                        is Resource.Error -> ErrorMessage(message = (trendingMovies as Resource.Error).message)
//
//                        is Resource.Loading -> LoadingIndicator()
//                    }
                }
            }
//            Column(
//                modifier = Modifier.padding(padding)
//            ) {
//                Text("Now Playing",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(8.dp) )
//
//                when(nowPlayingMovies){
//                    is Resource.Success -> {
//                        val movies = (nowPlayingMovies as Resource.Success).data?: emptyList()
//                        LazyRow {
//                            items(movies){
//                                movie ->
//                                MovieItem(movie=movie, onClick = {
//                                    navController.currentBackStackEntry
//                                        ?.savedStateHandle
//                                        ?.set("selectedMovie", movie)
//                                    navController.navigate("details")
//                                })
//                            }
//                        }
//                    }
//
//                    is Resource.Error -> ErrorMessage(message = (nowPlayingMovies as Resource.Error).message)
//
//                    is Resource.Loading -> LoadingIndicator()
//                }

//                Text("Trending",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(8.dp) )
//                when(trendingMovies){
//                    is Resource.Success -> {
//                        val movies = (trendingMovies as Resource.Success).data?: emptyList()
//                        LazyRow {
//                            items(movies){
//                                    movie ->
//                                MovieItem(movie=movie, onClick = {
//                                    navController.currentBackStackEntry
//                                        ?.savedStateHandle
//                                        ?.set("selectedMovie", movie)
//                                    navController.navigate("details")
//                                    // TODO navController.navigate("details/${movie.id}")
//                                })
//                            }
//                        }
//                    }
//
//                    is Resource.Error -> ErrorMessage(message = (trendingMovies as Resource.Error).message)
//
//                    is Resource.Loading -> LoadingIndicator()
//                }



        }
    )
}