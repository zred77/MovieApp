package com.veresz.movieapp.repository.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie

class MovieListDataSourceFactory(
        private val api: TmdbApi) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<MovieListDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val source = MovieListDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }
}