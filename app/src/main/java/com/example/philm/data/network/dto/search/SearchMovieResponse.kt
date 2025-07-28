package com.example.philm.data.network.dto.search

import com.google.gson.annotations.SerializedName
import com.example.philm.data.network.dto.movie.MovieResult

data class SearchMovieResponse(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val results: List<MovieResult>? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)
