package com.example.philm.data.network.mapper

import com.example.philm.data.local.entities.series.PopularSeriesEntity
import com.example.philm.data.local.entities.series.SeriesEntity
import com.example.philm.data.local.entities.series.TopRatedSeriesEntity
import com.example.philm.data.local.entities.series.details.SeriesDetailsEntity
import com.example.philm.data.local.entities.series.details.SeriesDetailsWithSeasons
import com.example.philm.data.local.entities.series.details.SeriesRecommendationEntity
import com.example.philm.data.local.entities.series.details.SeriesSeasonEntity
import com.example.philm.data.network.dto.series.SeriesResult
import com.example.philm.data.network.dto.series.details.Season
import com.example.philm.data.network.dto.series.details.SeriesDetailsDto
import com.example.philm.domain.model.series.Series
import com.example.philm.domain.model.series.SeriesDetails
import com.example.philm.domain.model.series.SeriesSeason

fun SeriesDetailsDto.toSeriesDetails(): SeriesDetails =
    SeriesDetails(
        backdropPath = backdropPath,
        casts = credits?.cast?.joinToString(", ") { it.name.orEmpty() },
        createdBy = createdBy?.joinToString(", ") { it.name.orEmpty() },
        firstAirDate = firstAirDate,
        genres = genres?.joinToString(", ") { it.name.orEmpty() },
        id = id,
        name = name,
        numberOfSeasons = numberOfSeasons,
        overview = overview,
        recommendations = recommendations?.results?.map { it.toSeries() },
        seasons = seasons?.map { it.toSeriesSeason() },
    )

fun Season.toSeriesSeason(): SeriesSeason =
    SeriesSeason(
        airDate = airDate,
        episodeCount = episodeCount,
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
    )

fun SeriesResult.toSeries(): Series =
    Series(
        firstAirDate = firstAirDate,
        id = id,
        name = name,
        posterPath = posterPath,
        voteAverage = voteAverage,
    )

fun SeriesResult.toPopularSeriesEntity(): PopularSeriesEntity =
    PopularSeriesEntity(
        series =
            SeriesEntity(
                firstAirDate = firstAirDate,
                name = name,
                posterPath = posterPath,
                seriesId = id,
                voteAverage = voteAverage,
            ),
    )

fun PopularSeriesEntity.toSeries(): Series =
    Series(
        firstAirDate = series.firstAirDate,
        id = series.seriesId,
        name = series.name,
        posterPath = series.posterPath,
        voteAverage = series.voteAverage,
    )

fun SeriesResult.toTopRatedSeriesEntity(): TopRatedSeriesEntity =
    TopRatedSeriesEntity(
        series =
            SeriesEntity(
                firstAirDate = firstAirDate,
                name = name,
                posterPath = posterPath,
                seriesId = id,
                voteAverage = voteAverage,
            ),
    )

fun TopRatedSeriesEntity.toSeries(): Series =
    Series(
        firstAirDate = series.firstAirDate,
        id = series.seriesId,
        name = series.name,
        posterPath = series.posterPath,
        voteAverage = series.voteAverage,
    )

fun SeriesDetailsWithSeasons.toSeriesDetails(): SeriesDetails =
    SeriesDetails(
        backdropPath = seriesDetails.backdropPath,
        casts = seriesDetails.casts,
        createdBy = seriesDetails.createdBy,
        firstAirDate = seriesDetails.firstAirDate,
        genres = seriesDetails.genres,
        id = seriesDetails.seriesId,
        name = seriesDetails.name,
        numberOfSeasons = seriesDetails.numberOfSeasons,
        overview = seriesDetails.overview,
        recommendations = recommendations.map { it.toSeries() },
        seasons = seasons.map { it.toSeriesSeason() },
    )

fun SeriesSeasonEntity.toSeriesSeason(): SeriesSeason =
    SeriesSeason(
        airDate = airDate,
        episodeCount = episodeCount,
        id = seasonId,
        name = name,
        overview = overview,
        posterPath = posterPath,
    )

fun SeriesDetails.toSeriesDetailsWithSeasons(lastUpdated: Long): SeriesDetailsWithSeasons =
    SeriesDetailsWithSeasons(
        seriesDetails =
            SeriesDetailsEntity(
                seriesId = id!!,
                backdropPath = backdropPath,
                casts = casts,
                createdBy = createdBy,
                firstAirDate = firstAirDate,
                genres = genres,
                lastUpdated = lastUpdated,
                name = name,
                numberOfSeasons = numberOfSeasons,
                overview = overview,
            ),
        recommendations = recommendations?.map { it.toSeriesRecommendationEntity() } ?: emptyList(),
        seasons = seasons?.map { it.toSeriesSeasonEntity(id) } ?: emptyList(),
    )

fun SeriesSeason.toSeriesSeasonEntity(seriesId: Int): SeriesSeasonEntity =
    SeriesSeasonEntity(
        seasonId = id!!,
        airDate = airDate,
        episodeCount = episodeCount,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seriesId = seriesId,
    )

fun SeriesRecommendationEntity.toSeries(): Series =
    Series(
        firstAirDate = series.firstAirDate,
        id = series.seriesId,
        name = series.name,
        posterPath = series.posterPath,
        voteAverage = series.voteAverage,
    )

fun Series.toSeriesRecommendationEntity(): SeriesRecommendationEntity =
    SeriesRecommendationEntity(
        recommendationId = id!!,
        series =
            SeriesEntity(
                firstAirDate = firstAirDate,
                name = name,
                posterPath = posterPath,
                seriesId = id,
                voteAverage = voteAverage,
            ),
    )