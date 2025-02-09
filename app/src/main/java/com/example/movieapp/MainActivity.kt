package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.Navigation.NavGraph
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.ui.theme.MovieAPPTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
   // @Inject lateinit var loginViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(applicationContext as MyApplication).appComponent.inject(this)
        //(application as ).appComponent.inject(this)
        enableEdgeToEdge()
        setContent {
            MovieAPPTheme {
                //val navController = rememberNavController()
                NavGraph()
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
            }
        }
    }
}
