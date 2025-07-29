package com.example.philm.data.local.entities.movie.details

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "recommendationId"])
data class MovieRecommendationCrossRef(
    val movieId: Int,
    val recommendationId: Int,
)
