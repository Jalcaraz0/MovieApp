package com.example.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movieapp.R
import com.example.movieapp.core.Resource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.remote.MovieDataSource
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.databinding.PopularMoviesBinding
import com.example.movieapp.databinding.TopRatedMoviesBinding
import com.example.movieapp.presentation.MovieViewModel
import com.example.movieapp.presentation.MovieViewModelFactory
import com.example.movieapp.repository.MovieRepositoryImpl
import com.example.movieapp.repository.RetrofitClient
import com.example.movieapp.ui.movie.adapters.MovieAdapter
import com.example.movieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.UpComingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservie)
            )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        //binding.progressBar.visibility =View.GONE
        concatAdapter = ConcatAdapter()

        //Main Observer
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpComingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })
        /* //Primera funcionalidad con los observer individuales

        viewModel.fetchUpcomingMovies().observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading->{
                    Log.d("LiveDataUpComingMovies","Loading...")
                }
                is Resource.Success->{
                    Log.d("LiveDataUpComingMovies","${it.data}")
                }
                is Resource.Failure->{
                    Log.d("LiveDataUpComingMovies","${it.exception}")
                }
            }
        })

        //Observer para el popular movies
        viewModel.fetchPopularMovies().observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading->{
                    Log.d("LiveDataPopularMovies","Loading...")
                }
                is Resource.Success->{
                    Log.d("LiveDataPopularMovies","${it.data}")
                }
                is Resource.Failure->{
                    Log.d("LiveDataPopularMovies","${it.exception}")
                }
            }
        })

        //Top Rated movies observer
        viewModel.fetchTopRatedMovies().observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading->{
                    Log.d("LiveDataTopRated","Loading...")
                }
                is Resource.Success->{
                    Log.d("LiveDataTopRated","${it.data}")
                }
                is Resource.Failure->{
                    Log.d("LiveDataTopRated","${it.exception}")
                }
            }
        }) */
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_account,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }


}