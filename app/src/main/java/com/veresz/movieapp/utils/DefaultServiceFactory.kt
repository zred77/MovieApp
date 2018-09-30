package com.veresz.movieapp.utils

import com.veresz.movieapp.repository.IMovieRepository
import com.veresz.movieapp.repository.MovieRepository

class DefaultServiceFactory : ServiceFactory {

    override fun getRepository(): IMovieRepository {
        return MovieRepository()
    }
}