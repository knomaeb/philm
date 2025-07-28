package com.example.philm.data.local.entities.series.details

import androidx.room.Entity

@Entity(primaryKeys = ["seriesId", "recommendationId"])
data class SeriesRecommendationCrossRef(
    val seriesId: Int,
    val recommendationId: Int,
)
