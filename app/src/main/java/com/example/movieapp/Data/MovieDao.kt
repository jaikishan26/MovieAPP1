package com.example.movieapp.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAMovie(movie:MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMovies(movies: List<MovieEntity>)

    @Query(" SELECT * FROM `movie-table` WHERE page = :page AND movieType = :movieType")
    abstract fun getAllMovies(page:Int, movieType:String): Flow<List<MovieEntity>>

    @Query(" SELECT * FROM `movie-table` WHERE isBookmarked = 1")
    abstract fun getBookmarkedMovies(): Flow<List<MovieEntity>>

    @Query(" SELECT * FROM `movie-table` WHERE id=:movieId")
    abstract suspend fun getMovieById(movieId:Int): MovieEntity

    @Update
    abstract suspend fun updateMovie(movie:MovieEntity)

}