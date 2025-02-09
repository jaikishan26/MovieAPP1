package com.example.movieapp

import android.app.Application
import com.example.movieapp.DependencyInjection.ApplicationComponent
import com.example.movieapp.DependencyInjection.DaggerApplicationComponent


//Entry point of the App. Initialises Dagger and sets up App context

class MyApplication: Application() {
    // Reference to the application graph that is used across the whole app
    lateinit var appComponent: ApplicationComponent

    override fun onCreate(){
        super.onCreate()

        appComponent = DaggerApplicationComponent.factory().create(this)
    }

}