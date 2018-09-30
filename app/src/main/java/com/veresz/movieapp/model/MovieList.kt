package com.veresz.movieapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieList(@Json(name = "page")
                     var page: Int? = null,
                     @Json(name = "results")
                     var results: List<Movie> = listOf(),
                     @Json(name = "total_pages")
                     var totalPages: Int? = null,
                     @Json(name = "total_results")
                     var totalResults: Int? = null,
                     @Json(name = "status_message")
                     var statusMessage: String? = "",
                     @Json(name = "status_code")
                     var statusCode: Int? = null)