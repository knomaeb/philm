package com.example.philm.data.network.dto.series

import com.google.gson.annotations.SerializedName

data class SeriesResponseDto(
    val page: Int? = null,
    val results: List<SeriesResult>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
)
