package com.veresz.movieapp.model

import android.os.Parcelable
import android.text.TextUtils
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.veresz.movieapp.BuildConfig
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(@Json(name = "id")
                 var id: Int? = null,
                 @Json(name = "overview")
                 var overview: String = "",
                 @Json(name = "title")
                 var title: String = "",
                 @Json(name = "poster_path")
                 var posterPath: String? = "",
                 @Json(name = "backdrop_path")
                 var backdropPath: String? = "",
                 @Json(name = "status_message")
                 var statusMessage: String? = "",
                 @Json(name = "status_code")
                 var statusCode: Int? = null) : Parcelable {

    fun imageUrl(path: String?): String {
        return if (!TextUtils.isEmpty(path)) {
            BuildConfig.TMDB_BASE_IMAGE_URL + path
        } else {
            ""
        }
    }
}
