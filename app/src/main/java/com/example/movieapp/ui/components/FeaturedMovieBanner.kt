package com.example.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant

@Composable
fun FeaturedMovieBanner(movie: Movie, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .clickable {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("selectedMovie",movie)
                navController.navigate("details/${movie.id}")
            }
    ) {
        AsyncImage(
            model = "${Constant.IMAGE_BASE_URL}${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
        )
        //Gradient Overlay
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Text(text = "#1 Now Playing",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
        }

    }
}