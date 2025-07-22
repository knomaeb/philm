package com.example.philm.data.network.dto.movie.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailResponseDto(
    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("genres")
    val genres: List<Genre?>? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("status")
    val status: String? = null
) : Parcelable