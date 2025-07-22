package com.example.philm.data.network.dto.movie.credits

import com.google.gson.annotations.SerializedName

data class CastResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
)