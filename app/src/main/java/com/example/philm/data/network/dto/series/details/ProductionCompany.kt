package com.example.philm.data.network.dto.series.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCompany(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("logo_path")
    val logo_path: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("origin_country")
    val origin_country: String? = null
) : Parcelable