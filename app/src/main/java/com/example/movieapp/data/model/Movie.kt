package com.example.movieapp.data.model

import android.text.BoringLayout

data class Movie(
    val id: Int = -1,
    val adult: Boolean = false,
    val genre_ids: List<Int> = listOf(),
    val backdrop_path: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path:String="",
    val release_date: String = "",
    val title:String="",
    val video:Boolean=false,
    val vote_average: Double = 0.0,
    val vote_account: Int = -1
)

data class MovieList(val results:List<Movie> = listOf())