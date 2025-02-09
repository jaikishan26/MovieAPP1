package com.example.movieapp.Util

sealed class Screen(val route:String) {
    object HomeScreen:Screen("home")
    object DetailScreen:Screen("details")
    object SearchScreen:Screen("search")
    object SavedScreen:Screen("saved")
}