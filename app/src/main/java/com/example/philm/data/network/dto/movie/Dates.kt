package com.example.philm.data.network.dto.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Dates(
    @SerializedName("maximum")
    val maximum: String? = null,

    @SerializedName("minimum")
    val minimum: String? = null
)
