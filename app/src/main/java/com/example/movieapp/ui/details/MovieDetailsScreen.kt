package com.example.movieapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant
import com.example.movieapp.Util.Resource
import com.example.movieapp.ui.components.LoadingIndicator
import com.example.movieapp.ui.components.shareMovie
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen(
    navController: NavController, movieId: Int, viewModel:MovieDetailsViewModel //  done to handle deeplink scenario
){

    val savedState = navController.previousBackStackEntry?.savedStateHandle
    val selectedMovie = savedState?.get<MovieEntity>("selectedMovie")

    LaunchedEffect(selectedMovie) {
        selectedMovie?.let { viewModel.setMovieDetails(it) }
    }

    //Fetching Movie Details if coming from Deep link
    LaunchedEffect(movieId){
        if(selectedMovie==null && movieId>0)
        {
            viewModel.fetchMovieDetails(movieId)
        }
    }

    val movieState by viewModel.movie.collectAsState()
    val savedMovies by viewModel.savedMovies.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Black, Color.DarkGray)
            )
        ).padding(WindowInsets.systemBars.asPaddingValues()).padding(16.dp)
    ) {
        when(movieState){
            is Resource.Loading -> CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
            is Resource.Success -> {
                val movie = (movieState as Resource.Success).data ?: return
                var isSavedState by remember { mutableStateOf(false) }

                LaunchedEffect(movie.id) {
                    isSavedState = viewModel.isMovieSaved(movie.id)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Movie Poster

                    if (movie != null) {
                        AsyncImage(
                            model = "${Constant.IMAGE_BASE_URL}${movie.posterPath}",
                            contentDescription = "${movie.title} Poster",
                            modifier = Modifier.size(250.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    //Titke of movie
                    if (movie != null) {
                        Text(text = movie.title,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }


                    //Buttons
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.toggleBookmark(movie)
                                    isSavedState = !isSavedState
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                        ) {
                            Icon(
                                imageVector = if(isSavedState) Icons.Default.Check else Icons.Default.Add,
                                contentDescription = "My List",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "My List",
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                shareMovie(context, movie)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "share", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Share", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //OverView
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (movie != null) {
                        Text(text = movie.overview,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            //textAlign = TextAlign.Justify,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.verticalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            is Resource.Error -> (movieState as Resource.Error).message?.let { Text(text = it, color = Color.Red,
                modifier = Modifier.align(Alignment.Center)) }
        }
    }

}