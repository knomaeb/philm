package com.example.philm.data.repository

import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.local.entities.movie.details.MovieDetailsEntity
import com.example.philm.data.local.entities.movie.details.MovieDetailsWithRecommendations
import com.example.philm.data.local.entities.movie.details.MovieRecommendationCrossRef
import com.example.philm.data.local.entities.movie.details.MovieRecommendationEntity
import com.example.philm.domain.repository.MovieDetailsLocalRepository

class MovieDetailsLocalRepositoryImpl(
    database: PhilmDatabase,
) : MovieDetailsLocalRepository {
    private val movieDetailsDao = database.movieDetailsDao()

    override suspend fun getCachedMovieDetailsById(id: Int): MovieDetailsWithRecommendations? =
        movieDetailsDao.getMovieDetailsWithRecommendations(id)

    override suspend fun clearCacheById(id: Int) {
        movieDetailsDao.deleteMovieDetailsWithCrossRefs(id)
    }

    override suspend fun saveMovieDetails(
        movieDetails: MovieDetailsEntity,
        recommendations: List<MovieRecommendationEntity>,
        movieRecommendationCrossRefs: List<MovieRecommendationCrossRef>,
    ) {
        movieDetailsDao.upsertMovieDetailsWithRecommendations(
            movieDetails = movieDetails,
            recommendations = recommendations,
            movieRecommendationCrossRefs = movieRecommendationCrossRefs,
        )
    }
}