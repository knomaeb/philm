package com.example.philm.domain.repository

import com.example.philm.data.local.entities.series.details.SeriesDetailsEntity
import com.example.philm.data.local.entities.series.details.SeriesDetailsWithSeasons
import com.example.philm.data.local.entities.series.details.SeriesRecommendationCrossRef
import com.example.philm.data.local.entities.series.details.SeriesRecommendationEntity
import com.example.philm.data.local.entities.series.details.SeriesSeasonCrossRef
import com.example.philm.data.local.entities.series.details.SeriesSeasonEntity

interface SeriesDetailsLocalRepository {
    suspend fun getSeriesDetailsById(id: Int): SeriesDetailsWithSeasons?

    suspend fun clearCacheById(id: Int)

    suspend fun saveSeriesDetails(
        seriesDetails: SeriesDetailsEntity,
        seriesSeasons: List<SeriesSeasonEntity>,
        seriesRecommendations: List<SeriesRecommendationEntity>,
        seriesSeasonCrossRef: List<SeriesSeasonCrossRef>,
        seriesRecommendationCrossRef: List<SeriesRecommendationCrossRef>,
    )
}