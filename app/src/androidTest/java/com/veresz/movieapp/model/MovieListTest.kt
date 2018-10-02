package com.veresz.movieapp.model

import androidx.test.runner.AndroidJUnit4
import com.veresz.movieapp.getMovieList
import com.veresz.movieapp.testParcel
import org.junit.*
import org.junit.runner.*
import java.util.Random
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class MovieListTest {

    @Test
    fun movieList_is_parcelable() {
        val movieList = MovieList()
        movieList.page = 123
        movieList.results = getMovieList(3)
        movieList.statusCode = Random().nextInt()
        movieList.statusMessage = UUID.randomUUID().toString()
        movieList.totalPages = Random().nextInt()
        movieList.totalResults = Random().nextInt()

        val newMovie = movieList.testParcel()
        Assert.assertEquals(movieList.page, newMovie.page)
        Assert.assertEquals(movieList.results, newMovie.results)
        Assert.assertEquals(movieList.statusCode, newMovie.statusCode)
        Assert.assertEquals(movieList.statusMessage, newMovie.statusMessage)
        Assert.assertEquals(movieList.totalPages, newMovie.totalPages)
        Assert.assertEquals(movieList.totalResults, newMovie.totalResults)

    }

}