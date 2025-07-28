package com.example.philm.data.repository

import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.local.entities.series.details.SeriesDetailsEntity
import com.example.philm.data.local.entities.series.details.SeriesDetailsWithSeasons
import com.example.philm.data.local.entities.series.details.SeriesRecommendationCrossRef
import com.example.philm.data.local.entities.series.details.SeriesRecommendationEntity
import com.example.philm.data.local.entities.series.details.SeriesSeasonCrossRef
import com.example.philm.data.local.entities.series.details.SeriesSeasonEntity
import com.example.philm.domain.repository.SeriesDetailsLocalRepository

class SeriesDetailsLocalRepositoryImpl(
    database: PhilmDatabase,
) : SeriesDetailsLocalRepository {
    private val seriesDetailsDao = database.seriesDetailsDao()

    override suspend fun getSeriesDetailsById(id: Int): SeriesDetailsWithSeasons? = seriesDetailsDao.getSeriesDetailsWithSeasons(id)

    override suspend fun clearCacheById(id: Int) {
        seriesDetailsDao.deleteSeriesDetailsWithCrossRefs(id)
    }

    override suspend fun saveSeriesDetails(
        seriesDetails: SeriesDetailsEntity,
        seriesSeasons: List<SeriesSeasonEntity>,
        seriesRecommendations: List<SeriesRecommendationEntity>,
        seriesSeasonCrossRef: List<SeriesSeasonCrossRef>,
        seriesRecommendationCrossRef: List<SeriesRecommendationCrossRef>,
    ) {
        seriesDetailsDao.upsertMovieDetailsWithRecommendations(
            seriesDetails = seriesDetails,
            seriesSeasons = seriesSeasons,
            seriesRecommendations = seriesRecommendations,
            seriesSeasonCrossRef = seriesSeasonCrossRef,
            seriesRecommendationCrossRef = seriesRecommendationCrossRef,
        )
    }
}