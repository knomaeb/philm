package com.example.philm.data.network.dto.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class MovieRangeResponseDto(
    @SerializedName("dates")
    val dates: Dates? = null,
    val page: Int? = null,
    val results: List<MovieResult>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
)