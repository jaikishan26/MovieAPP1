package com.example.movieapp.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.Movie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAMovie(movie:MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<MovieEntity>)

    @Query(" SELECT * FROM `movie-table`")
    abstract fun getAllMovies(): Flow<List<MovieEntity>>

    @Query(" SELECT * FROM `movie-table` WHERE isBookmarked = 1")
    abstract fun getBookmarkedMovies(): Flow<List<MovieEntity>>

    @Query(" SELECT * FROM `movie-table` WHERE id=:movieId")
    abstract suspend fun getMovieById(movieId:Int): MovieEntity

    @Update
    abstract suspend fun updateMovie(movie:MovieEntity)

}