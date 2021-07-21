package com.example.movieapp.repository

import android.os.Build
import com.example.movieapp.application.AppConstants
import com.example.movieapp.data.model.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey: String): MovieList

    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieList

    @GET("top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): MovieList
}

object RetrofitClient{
    val webservie by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}