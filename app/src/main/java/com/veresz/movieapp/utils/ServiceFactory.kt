package com.veresz.movieapp.utils

import androidx.annotation.VisibleForTesting
import com.veresz.movieapp.repository.IMovieRepository

interface ServiceFactory {

    companion object {
        private val LOCK = Any()
        private var instance: ServiceFactory? = null
        fun instance(): ServiceFactory {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceFactory()
                }
                return instance!!
            }
        }

        @VisibleForTesting
        fun changeInstance(locator: ServiceFactory) {
            instance = locator
        }
    }

    fun getRepository(): IMovieRepository
}