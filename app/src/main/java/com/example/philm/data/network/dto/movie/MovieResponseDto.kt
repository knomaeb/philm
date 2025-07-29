package com.example.philm.data.network.dto.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieResult>? = null,
    @SerializedName("total_pages")
    val total_pages: Int? = null,
    @SerializedName("total_results")
    val total_results: Int? = null
)