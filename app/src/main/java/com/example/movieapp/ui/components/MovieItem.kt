package com.example.movieapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Movie
import com.example.movieapp.Util.Constant


@Composable
fun MovieItem(movie: Movie, onClick:()->Unit){
    Column(
        modifier = Modifier
            .width(135.dp)
            .padding(8.dp)
            .clickable { onClick()},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "${Constant.IMAGE_BASE_URL}${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier.width(140.dp).height(200.dp).clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
//    /*Card(
//
//    ) {
//        Row(
//            modifier = Modifier.padding(8.dp)
//        ) {
//            AsyncImage(
//                model = "${Constant.IMAGE_BASE_URL}${movie.poster_path}",
//                contentDescription = movie.title,
//                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp))
//            )
//            Column(
//                modifier = Modifier.padding(start = 8.dp)
//            ) {
//                Text(
//                    text = movie.title, style = MaterialTheme.typography.headlineLarge
//                )
//                Text(text = movie.overview, maxLines = 2, overflow = TextOverflow.Ellipsis)
//            }
//        }
//    }*/
}