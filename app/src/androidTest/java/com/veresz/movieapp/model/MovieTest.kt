package com.veresz.movieapp.model

import androidx.test.runner.AndroidJUnit4
import com.veresz.movieapp.getRandomMovie
import com.veresz.movieapp.testParcel
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class MovieTest {

    @Test
    fun movie_is_parcelable() {
        val movie = getRandomMovie()

        val newMovie = movie.testParcel()
        Assert.assertEquals(movie.id, newMovie.id)
        Assert.assertEquals(movie.title, newMovie.title)
        Assert.assertEquals(movie.backdropPath, newMovie.backdropPath)
        Assert.assertEquals(movie.posterPath, newMovie.posterPath)
        Assert.assertEquals(movie.overview, newMovie.overview)
    }

}