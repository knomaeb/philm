package com.example.philm.data.network.dto.trending

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrendingResult(
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    val id: Int? = null,
    @SerializedName("media_type")
    val mediaType: String? = null,
    val name: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
) : Parcelable
