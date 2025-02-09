package com.example.movieapp.Util

import com.example.movieapp.Data.MovieEntity
import kotlinx.coroutines.flow.Flow

//Wrapper class used to manage different states of data
//allows to efficiently handle API responses
sealed class Resource<T> ( val data:T? = null, val message:String? = null){

    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T?=null): Resource<T>(data,message)
    class Loading<T>(data: T?=null): Resource<T>(data)
}