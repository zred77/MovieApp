package com.veresz.movieapp.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.veresz.movieapp.api.LiveDataResource
import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.repository.datasource.MovieListDataSourceFactory

class MovieRepository : IMovieRepository {

    private var queryFilter = MutableLiveData<String>()
    private var api: TmdbApi = TmdbApi.create()
    private var upcoming = MutableLiveData<Boolean>()
    private lateinit var sourceFactory: MovieListDataSourceFactory

    override fun movieList(query: String?, page: Int): LiveDataResource<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(true)
                .build()
        sourceFactory = MovieListDataSourceFactory(api, queryFilter, upcoming)
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

    override fun setQueryFilter(queryFilter: String) {
        this.queryFilter.value = queryFilter
    }

    override fun setUpcoming(upcoming: Boolean) {
        this.upcoming.value = upcoming
    }

    @VisibleForTesting
    fun setFakeApi(api: TmdbApi) {
        this.api = api
    }
}