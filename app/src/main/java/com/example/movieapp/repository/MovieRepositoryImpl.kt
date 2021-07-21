package com.example.movieapp.repository

import com.example.movieapp.data.model.MovieList
import com.example.movieapp.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource) : MovieRepository {
    override suspend fun getUpComingMovies(): MovieList = dataSource.getUpComingMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()
}