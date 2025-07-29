package com.example.philm.domain.repository

import com.example.philm.data.local.entities.movie.details.MovieDetailsEntity
import com.example.philm.data.local.entities.movie.details.MovieDetailsWithRecommendations
import com.example.philm.data.local.entities.movie.details.MovieRecommendationCrossRef
import com.example.philm.data.local.entities.movie.details.MovieRecommendationEntity

interface MovieDetailsLocalRepository {
    suspend fun getCachedMovieDetailsById(id: Int): MovieDetailsWithRecommendations?

    suspend fun clearCacheById(id: Int)

    suspend fun saveMovieDetails(
        movieDetails: MovieDetailsEntity,
        recommendations: List<MovieRecommendationEntity>,
        movieRecommendationCrossRefs: List<MovieRecommendationCrossRef>,
    )
}