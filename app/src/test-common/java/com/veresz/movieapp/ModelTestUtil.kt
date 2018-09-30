package com.veresz.movieapp

import com.veresz.movieapp.model.Movie
import java.util.UUID

fun getMovieList(size: Int): List<Movie> {
    return Array(size) { getRandomMovie() }.asList()
}

fun getRandomMovie(): Movie {
    return Movie().apply {
        id = 123
        title = UUID.randomUUID().toString()
        backdropPath = UUID.randomUUID().toString()
        posterPath = UUID.randomUUID().toString()
        overview = UUID.randomUUID().toString()
    }
}