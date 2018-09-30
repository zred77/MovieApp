package com.veresz.movieapp.repository

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.veresz.movieapp.api.LiveDataResource
import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.repository.datasource.MovieListDataSourceFactory

class MovieRepository : IMovieRepository {

    private val api: TmdbApi by lazy {
        TmdbApi.create()
    }

    override fun movieList(query: String?, page: Int): LiveDataResource<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(true)
                .build()
        val sourceFactory = MovieListDataSourceFactory(api)
        val livePagedList = LivePagedListBuilder(sourceFactory, config)
                .build()
        return LiveDataResource(livePagedList,
                Transformations.switchMap(sourceFactory.sourceLiveData) { it.networkStatus },
                Transformations.switchMap(sourceFactory.sourceLiveData) { it.initialLoad },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                })
    }
}