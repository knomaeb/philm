package com.example.philm.data.local.entities.series.details

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.philm.data.local.entities.series.SeriesEntity

@Entity(tableName = "series_recommendations")
data class SeriesRecommendationEntity(
    @PrimaryKey val recommendationId: Int,
    @Embedded val series: SeriesEntity,
)
