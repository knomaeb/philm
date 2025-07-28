package com.example.philm.data.network.dto.series.credits

import com.google.gson.annotations.SerializedName

data class SeriesCreditsDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("cast")
    val cast: List<Cast>? = null,
    @SerializedName("crew")
    val crew: List<Crew>? = null,
)