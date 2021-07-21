package com.example.movieapp.repository

import com.example.movieapp.data.model.MovieList

interface MovieRepository {

   suspend fun getUpComingMovies(): MovieList
   suspend fun getPopularMovies(): MovieList
   suspend fun getTopRatedMovies(): MovieList
}