package com.veresz.movieapp.api

import com.veresz.movieapp.BuildConfig
import com.veresz.movieapp.model.MovieList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("3/movie/now_playing")
    fun nowPlaying(@Query("language") language: String? = null,
                   @Query("page") page: Int = 1): Call<MovieList>

    @GET("3/search/movie")
    fun searchMovie(@Query("query") query: String? = null,
                    @Query("language") language: String? = null,
                    @Query("page") page: Int = 1): Call<MovieList>

    @GET("3/movie/upcoming")
    fun upcoming(@Query("language") language: String? = null,
                 @Query("page") page: Int = 1): Call<MovieList>

    companion object {

        fun create(): TmdbApi {
            val client = OkHttpClient.Builder()
                    .addInterceptor(TmdbInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(TmdbApi::class.java)
        }
    }
}