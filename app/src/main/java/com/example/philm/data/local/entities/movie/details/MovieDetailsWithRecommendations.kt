package com.example.philm.data.local.entities.movie.details

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieDetailsWithRecommendations(
    @Embedded val movieDetails: MovieDetailsEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "recommendationId",
        associateBy = Junction(MovieRecommendationCrossRef::class),
    )
    val recommendations: List<MovieRecommendationEntity>,
)
