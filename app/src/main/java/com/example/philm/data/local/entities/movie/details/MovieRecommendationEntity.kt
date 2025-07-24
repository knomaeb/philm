package com.example.philm.data.local.entities.movie.details

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.philm.data.local.entities.movie.MovieEntity

@Entity(tableName = "movie_recommendations")
data class MovieRecommendationEntity(
    @PrimaryKey val recommendationId: Int,
    @Embedded val movie: MovieEntity,
)
