package com.example.philm.data.network.dto.movie.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    @SerializedName("english_name")
    val english_name: String? = null,
    @SerializedName("iso_639_1")
    val iso_639_1: String? = null,
    @SerializedName("name")
    val name: String? = null
)