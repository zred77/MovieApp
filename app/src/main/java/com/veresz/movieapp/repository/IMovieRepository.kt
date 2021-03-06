package com.veresz.movieapp.repository

import androidx.paging.PagedList
import com.veresz.movieapp.api.LiveDataResource
import com.veresz.movieapp.model.Movie

interface IMovieRepository {

    fun movieList(query: String? = null, page: Int = 1): LiveDataResource<PagedList<Movie>>

    fun setQueryFilter(queryFilter: String)

    fun setUpcoming(upcoming: Boolean)
}