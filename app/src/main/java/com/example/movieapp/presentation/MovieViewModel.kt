package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieapp.core.Resource
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    //Fetch principal para las 3 llamadas
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(repo.getUpComingMovies(),repo.getPopularMovies(),repo.getTopRatedMovies())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }
}
    /* Fetchs individuales para cada llamada al servidor
    //UpComing Movies
    fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getUpComingMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }
    //Popular Movies
    fun fetchPopularMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getPopularMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }
    //Top Rated Movies
    fun fetchTopRatedMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getTopRatedMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    } */


class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }

}