package com.example.philm.data.network.dto.movie.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpokenLanguage(
    @SerializedName("english_name")
    val english_name: String,
    @SerializedName("iso_639_1")
    val iso_639_1: String,
    @SerializedName("name")
    val name: String
): Parcelable