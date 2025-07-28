package com.example.philm.data.network.mapper

import com.example.philm.data.local.entities.trending.TrendingEntity
import com.example.philm.data.network.dto.trending.TrendingResult
import com.example.philm.domain.model.trending.Trending

fun TrendingResult.toTrending(): Trending =
    Trending(
        backdropPath = backdropPath,
        id = id,
        mediaType = mediaType,
        name = name,
        posterPath = posterPath,
        title = title,
    )

fun TrendingEntity.toTrending(): Trending =
    Trending(
        backdropPath = backdropPath,
        id = trendingId,
        mediaType = mediaType,
        name = name,
        posterPath = posterPath,
        title = title,
    )

fun Trending.toTrendingEntity(): TrendingEntity =
    TrendingEntity(
        backdropPath = backdropPath,
        mediaType = mediaType,
        name = name,
        posterPath = posterPath,
        title = title,
        trendingId = id,
    )