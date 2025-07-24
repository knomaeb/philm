package com.example.philm.domain.use_cases.series

import com.example.philm.core.utils.CacheUtils
import com.example.philm.core.utils.Resource
import com.example.philm.data.local.entities.series.details.SeriesRecommendationCrossRef
import com.example.philm.data.local.entities.series.details.SeriesSeasonCrossRef
import com.example.philm.data.network.mapper.toSeriesDetails
import com.example.philm.data.network.mapper.toSeriesDetailsWithSeasons
import com.example.philm.domain.model.series.SeriesDetails
import com.example.philm.domain.repository.MovieRepository
import com.example.philm.domain.repository.SeriesDetailsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeriesDetailsUseCase(
    private val movieRepository: MovieRepository,
    private val seriesDetailsLocalRepository: SeriesDetailsLocalRepository,
) {
    operator fun invoke(
        seriesId: Int,
        appendToResponse: String? = null,
        language: String = "en-US",
    ): Flow<Resource<SeriesDetails>> =
        flow {
            try {
                emit(Resource.Loading)

                val cachedData = seriesDetailsLocalRepository.getSeriesDetailsById(seriesId)
                val lastUpdated = cachedData?.seriesDetails?.lastUpdated ?: 0

                if (cachedData != null && CacheUtils.isCacheValid(lastUpdated)) {
                    emit(Resource.Success(cachedData.toSeriesDetails()))
                    return@flow
                }

                val remoteDetails =
                    movieRepository
                        .getSeriesDetails(seriesId, appendToResponse, language)
                        .toSeriesDetails()
                val seriesDetailsWithSeasons =
                    remoteDetails.toSeriesDetailsWithSeasons(
                        System.currentTimeMillis(),
                    )

                seriesDetailsLocalRepository.saveSeriesDetails(
                    seriesDetails = seriesDetailsWithSeasons.seriesDetails,
                    seriesSeasons = seriesDetailsWithSeasons.seasons,
                    seriesRecommendations = seriesDetailsWithSeasons.recommendations,
                    seriesSeasonCrossRef =
                        seriesDetailsWithSeasons.seasons.map {
                            SeriesSeasonCrossRef(
                                seriesId = seriesDetailsWithSeasons.seriesDetails.seriesId,
                                seasonId = it.seasonId,
                            )
                        },
                    seriesRecommendationCrossRef =
                        seriesDetailsWithSeasons.recommendations.map {
                            SeriesRecommendationCrossRef(
                                seriesId = seriesDetailsWithSeasons.seriesDetails.seriesId,
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