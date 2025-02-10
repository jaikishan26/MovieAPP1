package com.example.movieapp.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.movieapp.Data.MovieEntity
import com.example.movieapp.Util.Screen
import com.example.movieapp.ui.Saved.SavedMovieScreen
import com.example.movieapp.ui.details.MovieDetailsScreen
import com.example.movieapp.ui.details.MovieDetailsViewModel
import com.example.movieapp.ui.home.HomeScreen
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.ui.search.SearchScreen
import com.example.movieapp.ui.search.SearchViewModel
import kotlin.reflect.typeOf


@Composable
fun NavGraph(navController: NavHostController, viewModel: HomeViewModel,
             detialViewModel: MovieDetailsViewModel, searchViewModel: SearchViewModel){
   NavHost(navController = navController,
       startDestination = "home") {

       composable("home"){
           HomeScreen(navController, viewModel)
       }

       composable(
           route = "details/{movieId}",
           arguments = listOf(navArgument("movieId"){type = NavType.IntType}),
           deepLinks = listOf(navDeepLink { uriPattern = "https://myapp.com/details/{movieId}" })
       ){ backStackEntry ->
           val movieId = backStackEntry.arguments?.getInt("movieId")?:-1
           MovieDetailsScreen(navController, movieId, detialViewModel)
           //backStackEntry ->
           //val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()

       }
       composable("search"){
           SearchScreen(navController, searchViewModel)
       }

       composable("saved"){
           SavedMovieScreen(navController, detialViewModel)
       }



       /*composable(Screen.DetailScreen.route+ "/{movieId}"){
           backStackEntry ->
           val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
           //MovieDetailScreen(movieId = movieId, navController = navController)
       }*/
       /*composable(Screen.SearchScreen.route){
           SearchScreen(navController)
       }

       composable(Screen.SavedScreen.route){
           SavedScreen(navController)
       }*/
   }
}