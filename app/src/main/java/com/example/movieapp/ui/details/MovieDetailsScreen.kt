package com.example.movieapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun MovieDetailsScreen(
    navController: NavController, movieId: Int, viewModel:MovieDetailsViewModel //  done to handle deeplink scenario
){
    //val viewModel: MovieDetailsViewModel = viewModel()
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
//        if(selectedMovie !=null){
//            viewModel.setMovieDetails((selectedMovie)) //set movie directly from Home Screen
//        }
//        else{
//            viewModel.fetchMovieDetails(movieId) //Fetch from APi if navigated via Deep LInk
//        }
    }

    val movieState by viewModel.movie.collectAsState()
    val savedMovies by viewModel.savedMovies.collectAsState()

    when(movieState){
        is Resource.Loading -> LoadingIndicator()
                                //CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        is Resource.Success -> {
            val movie = (movieState as Resource.Success).data ?: return
            val isSaved = movie.isBookmarked
            //val isSaved = movie?.id?.let { viewModel.isMovieSaved(it) }
            //val isSaved = savedMovies is Resource.Success && (savedMovies as Resource.Success).data!!.contains(movie)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Titke of movie
                if (movie != null) {
                    Text(text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())
                }
                Spacer(modifier = Modifier.height(16.dp))

                //Movie Poster

                if (movie != null) {
                    AsyncImage(
                        model = "${Constant.IMAGE_BASE_URL}${movie.posterPath}",
                        contentDescription = "${movie.title} Poster",
                        modifier = Modifier.size(400.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                    )
                }

                //MovieDetails
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                if (movie != null) {
                    Text(text = movie.overview,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.verticalScroll(rememberScrollState()))
                }

                Spacer(modifier = Modifier.height(24.dp))
//// BUtton not working properly
                Button(
                    onClick = {
                        if (isSaved == true) movie?.let { viewModel.removeMovie(it) }
                        else {
                            movie?.let { viewModel.saveMovie(it)
                        }
                    }
                        },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isSaved == true) "Remove from Saved" else "Save Movie")
                }
            }
        }
        is Resource.Error -> (movieState as Resource.Error).message?.let { Text(text = it, color = Color.Red) }
    }
}