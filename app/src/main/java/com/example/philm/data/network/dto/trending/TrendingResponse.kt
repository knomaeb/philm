package com.example.philm.data.network.dto.trending

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int? = null,
    val results: List<TrendingResult>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
)
