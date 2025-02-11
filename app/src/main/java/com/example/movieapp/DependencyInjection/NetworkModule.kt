package com.example.movieapp.DependencyInjection

import com.example.movieapp.ApiService
import com.example.movieapp.Util.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module  //Marks paricular class as Dagger based Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }

    @Provides // provides instance of retrofit, used for creating API service and uses okkhttp client  for network request
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder().baseUrl(Constant.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides // this provides an instance of APiserivce, interface for making API calls
    fun provideApiServie(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}

