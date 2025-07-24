package com.example.philm.domain.use_cases.movie

import com.example.philm.core.utils.CacheUtils
import com.example.philm.core.utils.Resource
import com.example.philm.data.local.entities.movie.details.MovieRecommendationCrossRef
import com.example.philm.data.network.mapper.toMovieDetails
import com.example.philm.data.network.mapper.toMovieDetailsWithRecommendations
import com.example.philm.domain.model.movie.MovieDetails
import com.example.philm.domain.repository.MovieDetailsLocalRepository
import com.example.philm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(
    private val movieDetailsLocalRepository: MovieDetailsLocalRepository,
    private val movieRepository: MovieRepository
) {

    operator fun invoke(
        movieId: Int,
        appendToResponse: String? = null,
        language: String = "en-US",
    ): Flow<Resource<MovieDetails>> =
        flow {
            try {
                emit(Resource.Loading)

                val cachedData = movieDetailsLocalRepository.getCachedMovieDetailsById(movieId)
                val lastUpdated = cachedData?.movieDetails?.lastUpdated ?: 0

                if (cachedData != null && CacheUtils.isCacheValid(lastUpdated)) {
                    emit(Resource.Success(cachedData.toMovieDetails()))
                    return@flow
                }

                val remoteDetails =
                    movieRepository
                        .getMovieDetails(movieId, appendToResponse, language)
                        .toMovieDetails()
                val movieDetailsWithRecommendations =
                    remoteDetails.toMovieDetailsWithRecommendations(
                        System.currentTimeMillis(),
                    )

                movieDetailsLocalRepository.saveMovieDetails(
                    movieDetails = movieDetailsWithRecommendations.movieDetails,
                    recommendations = movieDetailsWithRecommendations.recommendations,
                    movieRecommendationCrossRefs =
                        movieDetailsWithRecommendations.recommendations.map {
                            MovieRecommendationCrossRef(
                                movieId = movieDetailsWithRecommendations.movieDetails.movieId,
                                recommendationId = it.recommendationId,
                            )
                        },
                )

                emit(Resource.Success(remoteDetails))
            } catch (throwable: Throwable) {
                Resource.Error(throwable.localizedMessage!!, throwable)
            }
        }

}