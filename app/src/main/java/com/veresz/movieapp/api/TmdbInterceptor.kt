package com.veresz.movieapp.api

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class TmdbInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("api_key", "45b63fd44cc2a000444038d5a75aa89c").build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
