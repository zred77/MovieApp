package com.veresz.movieapp.repository.datasource

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.veresz.movieapp.api.NetworkStatus
import com.veresz.movieapp.api.NetworkStatus.ERROR
import com.veresz.movieapp.api.NetworkStatus.LOADING
import com.veresz.movieapp.api.NetworkStatus.SUCCESS
import com.veresz.movieapp.api.TmdbApi
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.model.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListDataSource(private val api: TmdbApi,
                          private var queryFilter: String?,
                          private val upcoming: Boolean? = false) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Any)? = null
    val networkStatus = MutableLiveData<NetworkStatus>()
    val initialLoad = MutableLiveData<NetworkStatus>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        val page = 1

        networkStatus.postValue(LOADING)
        initialLoad.postValue(LOADING)
        val retrofitCallback = object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                val nowPlaying = response.body()
                callback.onResult(nowPlaying!!.results,
                                  0,
                                  nowPlaying.results.size,
                                  null,
                                  page + 1)
                networkStatus.postValue(SUCCESS)
                initialLoad.postValue(SUCCESS)
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                retry = {
                    loadInitial(params, callback)
                }
                networkStatus.postValue(ERROR)
                initialLoad.postValue(ERROR)
            }
        }
        when {
            upcoming != null && upcoming -> api.upcoming().enqueue(retrofitCallback)
            TextUtils.isEmpty(queryFilter) -> api.nowPlaying().enqueue(retrofitCallback)
            else -> api.searchMovie(queryFilter).enqueue(retrofitCallback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        val page = params.key

        networkStatus.postValue(LOADING)
        val retrofitCallback = object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                val nowPlaying = response.body()

                callback.onResult(nowPlaying!!.results,
                                  page + 1)
                networkStatus.postValue(SUCCESS)
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                retry = {
                    loadAfter(params, callback)
                }
                networkStatus.postValue(ERROR)
            }
        }
        when {
            upcoming != null && upcoming -> api.upcoming(page = page.toInt()).enqueue(retrofitCallback)
            TextUtils.isEmpty(queryFilter) -> api.nowPlaying(page = page.toInt()).enqueue(retrofitCallback)
            else -> api.searchMovie(queryFilter).enqueue(retrofitCallback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    fun retryAllFailed() {
        retry?.invoke()
    }
}