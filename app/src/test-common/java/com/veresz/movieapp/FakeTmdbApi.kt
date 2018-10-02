package com.veresz.movieapp

import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.model.MovieList
import retrofit2.Call
import retrofit2.mock.Calls
import kotlin.math.min

class FakeTmdbApi : TmdbApi {

    var movies: List<Movie> = arrayListOf()

    override fun nowPlaying(language: String?, page: Int): Call<MovieList> {
        return getMovieList(null, null, page)
    }

    override fun searchMovie(query: String?, language: String?, page: Int): Call<MovieList> {
        return getMovieList(query, null, page)
    }

    private fun getMovieList(query: String?, language: String?, page: Int): Call<MovieList> {
        var movieListPage: List<Movie> = arrayListOf()
        if ((page - 1) * 20 < movies.size) {
            movieListPage = movies.subList((page - 1) * 20, min(page * 20, movies.size))
        }
        val movieList = MovieList(results = movieListPage,
                totalResults = movieListPage.size,
                totalPages = movieListPage.size.div(20) + 1)
        return Calls.response(movieList)
    }
}

