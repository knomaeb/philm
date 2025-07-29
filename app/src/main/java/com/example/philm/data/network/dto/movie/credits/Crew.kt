package com.example.philm.data.network.dto.movie.credits

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("credit_id")
    val credit_id: String? = null,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("job")
    val job: String? = null,
    @SerializedName("known_for_department")
    val known_for_department: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val original_name: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profile_path: String? = null
)