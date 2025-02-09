package com.example.movieapp.DependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.movieapp.ApiService
//import androidx.room.Database
import com.example.movieapp.Data.MovieDao
import com.example.movieapp.Data.MovieDatebase
import com.example.movieapp.Data.MovieMapper
import com.example.movieapp.Data.MovieRepository
import com.example.movieapp.MyApplication
//import com.example.movieapp.Data.MovieDatebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun  provideContext(application: MyApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideMovieDatabase(context: Context): MovieDatebase = Room
        .databaseBuilder(context.applicationContext, MovieDatebase::class.java,"movie_db").build()

//movie_db is name of file stored in device storage
//It's a container for storing data created using Room.databseBuilder. It can stor multiple tables.
//location in device = (data/data/com.yourapp/databases/movies_db
    @Provides
    @Singleton
    fun provideMovieDao(database:MovieDatebase):MovieDao {
    return database.movieDao()
}


    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService, movieDao: MovieDao, movieMapper: MovieMapper)
    :MovieRepository = MovieRepository(apiService, movieMapper,movieDao)

}