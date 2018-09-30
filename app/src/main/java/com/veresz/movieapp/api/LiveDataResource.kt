package com.veresz.movieapp.api

import androidx.lifecycle.LiveData

class LiveDataResource<out ResultType>(val data: LiveData<out ResultType>,
                                       val networkStatus: LiveData<NetworkStatus>,
                                       val refreshStatus: LiveData<NetworkStatus>,
                                       val refresh: () -> Unit,
                                       val retry: () -> Unit)
