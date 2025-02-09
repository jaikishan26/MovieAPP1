package com.example.movieapp

import com.example.movieapp.Util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

//service that alows us to get categories.hp file which we will convert to Json object
//create method provides access to service methods
    val movieService = retrofit.create(ApiService::class.java)



interface ApiService {
//    @GET("categories.php")
//    suspend fun getNowPlayingMovies(): MovieResponse


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey:String, @Query("page") page:Int): MovieResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") apiKey:String, @Query("page") page:Int): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query:String,
        @Query("api_key") apiKey: String): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId:Int,
                                @Query("api_key") apiKey: String): Movie

}