package com.example.philm.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.philm.data.local.entities.series.details.SeriesDetailsEntity
import com.example.philm.data.local.entities.series.details.SeriesDetailsWithSeasons
import com.example.philm.data.local.entities.series.details.SeriesRecommendationCrossRef
import com.example.philm.data.local.entities.series.details.SeriesRecommendationEntity
import com.example.philm.data.local.entities.series.details.SeriesSeasonCrossRef
import com.example.philm.data.local.entities.series.details.SeriesSeasonEntity

@Dao
interface SeriesDetailsDao {
    @Upsert
    suspend fun upsertSeriesDetails(seriesDetails: SeriesDetailsEntity)

    @Upsert
    suspend fun upsertSeriesSeasons(seriesSeasons: List<SeriesSeasonEntity>)

    @Upsert
    suspend fun upsertSeriesRecommendations(seriesRecommendations: List<SeriesRecommendationEntity>)

    @Upsert
    suspend fun upsertSeriesSeasonCrossRef(seriesSeasonCrossRef: List<SeriesSeasonCrossRef>)

    @Upsert
    suspend fun upsertSeriesRecommendationCrossRef(seriesRecommendationCrossRef: List<SeriesRecommendationCrossRef>)

    @Query("DELETE FROM series_details WHERE seriesId = :seriesId")
    suspend fun deleteSeriesDetails(seriesId: Int)

    @Query("DELETE FROM series_seasons WHERE seriesId = :seriesId")
    suspend fun deleteSeriesSeasons(seriesId: Int)

    @Query("DELETE FROM SeriesSeasonCrossRef WHERE seriesId = :seriesId")
    suspend fun deleteSeriesSeasonCrossRef(seriesId: Int)

    @Query("DELETE FROM SeriesRecommendationCrossRef WHERE seriesId = :seriesId")
    suspend fun deleteSeriesRecommendationCrossRef(seriesId: Int)

    @Transaction
    @Query("SELECT * FROM series_details WHERE seriesId = :seriesId")
    suspend fun getSeriesDetailsWithSeasons(seriesId: Int): SeriesDetailsWithSeasons?

    @Transaction
    suspend fun upsertMovieDetailsWithRecommendations(
        seriesDetails: SeriesDetailsEntity,
        seriesSeasons: List<SeriesSeasonEntity>,
        seriesRecommendations: List<SeriesRecommendationEntity>,
        seriesSeasonCrossRef: List<SeriesSeasonCrossRef>,
        seriesRecommendationCrossRef: List<SeriesRecommendationCrossRef>,
    ) {
        upsertSeriesDetails(seriesDetails)
        upsertSeriesSeasons(seriesSeasons)
        upsertSeriesRecommendations(seriesRecommendations)
        upsertSeriesSeasonCrossRef(seriesSeasonCrossRef)
        upsertSeriesRecommendationCrossRef(seriesRecommendationCrossRef)
    }

    @Transaction
    suspend fun deleteSeriesDetailsWithCrossRefs(id: Int) {
        deleteSeriesRecommendationCrossRef(id)
        deleteSeriesSeasonCrossRef(id)
        deleteSeriesSeasons(id)
        deleteSeriesDetails(id)
    }
}