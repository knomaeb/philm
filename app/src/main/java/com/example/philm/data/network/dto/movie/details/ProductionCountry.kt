package com.example.philm.data.network.dto.movie.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String? = null,
    @SerializedName("name")
    val name: String? = null
)