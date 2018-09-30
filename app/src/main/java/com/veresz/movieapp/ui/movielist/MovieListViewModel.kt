package com.veresz.movieapp.ui.movielist

import androidx.lifecycle.ViewModel
import com.veresz.movieapp.repository.IMovieRepository
import com.veresz.movieapp.utils.ServiceFactory

class MovieListViewModel : ViewModel() {

    private val repository: IMovieRepository = ServiceFactory.instance().getRepository()
    private val resource = repository.movieList()
    val movies = resource.data
    val networkStatus = resource.networkStatus
    val refreshStatus = resource.refreshStatus

    fun retry() {
        resource.retry.invoke()
    }

    fun refresh() {
        resource.refresh.invoke()
    }
    fun setQueryFilter(queryFilter: String) {
        repository.setQueryFilter(queryFilter)
        refresh()
    }
}