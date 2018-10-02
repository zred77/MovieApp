package com.veresz.movieapp

import com.veresz.movieapp.model.Movie
import java.util.Random
import java.util.UUID

fun getMovieList(size: Int): List<Movie> {
    return Array(size) { getRandomMovie() }.asList()
}

fun getRandomMovie(): Movie {
    return Movie().apply {
        id = Random().nextInt()
        title = UUID.randomUUID().toString()
        backdropPath = UUID.randomUUID().toString()
        posterPath = UUID.randomUUID().toString()
        overview = UUID.randomUUID().toString()
    }
}