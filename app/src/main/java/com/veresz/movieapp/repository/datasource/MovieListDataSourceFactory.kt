package com.veresz.movieapp.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie

class MovieListDataSourceFactory(
        private val api: TmdbApi,
        private val queryFilter: LiveData<String>,
        private val upcoming: MutableLiveData<Boolean>) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<MovieListDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val source = MovieListDataSource(api, queryFilter.value, upcoming.value)
        sourceLiveData.postValue(source)
        return source
    }
}