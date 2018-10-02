package com.veresz.movieapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.runner.AndroidJUnit4
import com.veresz.movieapp.FakeTmdbApi
import com.veresz.movieapp.api.LiveDataResource
import com.veresz.movieapp.getMovieList
import com.veresz.movieapp.getRandomMovie
import com.veresz.movieapp.model.Movie
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class MovieRepositoryTest {

    private val repository = MovieRepository()
    private val fakeApi: FakeTmdbApi = FakeTmdbApi()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Before
    fun before() {
        repository.setFakeApi(fakeApi)
    }

    @Test
    fun emptyList() {
        val movies = repository.movieList(page = 20)
        val pagedList = getPagedList(movies)
        assertThat(pagedList.size, `is`(0))
    }

    @Test
    fun oneItem() {
        val movie = getRandomMovie()
        fakeApi.movies = listOf(movie)
        val movies = repository.movieList()
        val pagedList = getPagedList(movies)
        assertThat(pagedList, `is`(listOf(movie)))
    }

    @Test
    fun completeList() {
        val movieList = getMovieList(100)
        fakeApi.movies = movieList
        val movies = repository.movieList()
        val pagedList = getPagedList(movies)
        pagedList.loadAround(movieList.size - 1)
        assertThat(pagedList, `is`(movieList))
    }

    private fun getPagedList(resource: LiveDataResource<PagedList<Movie>>): PagedList<Movie> {
        val observer = TestObserver<PagedList<Movie>>()
        resource.data.observeForever(observer)
        assertThat(observer.value, `is`(notNullValue()))
        return observer.value!!
    }

    private class TestObserver<T> : Observer<T> {

        var value: T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}