package com.example.philm.data.network.dto.movie

import com.google.gson.annotations.SerializedName

data class NowPlayingMovieResponse(
    @SerializedName("dates")
    val dates: Dates? = null,

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val results: List<Result>? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)
