package com.example.philm.domain.use_cases.trending

import com.example.philm.core.utils.Resource
import com.example.philm.core.utils.TimeWindow
import com.example.philm.data.network.mapper.toTrending
import com.example.philm.data.network.mapper.toTrendingEntity
import com.example.philm.domain.model.trending.Trending
import com.example.philm.domain.repository.MovieRepository
import com.example.philm.domain.repository.TrendingLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTrendingUseCase(
    private val movieRepository: MovieRepository,
    private val trendingLocalRepository: TrendingLocalRepository,
) {
    operator fun invoke(
        timeWindow: TimeWindow = TimeWindow.day,
        language: String = "en-US",
    ): Flow<Resource<List<Trending>>> =
        flow {
            try {
                emit(Resource.Loading)
                val trending =
                    if (trendingLocalRepository.isCacheValid()) {
                        trendingLocalRepository.getCachedTrending().map { it.toTrending() }
                    } else {
                        val results =
                            movieRepository
                                .getTrending(timeWindow, language)
                                .results
                                .orEmpty()
                                .map { it.toTrending() }
                        trendingLocalRepository.clearCache()
                        trendingLocalRepository.saveTrending(results.map { it.toTrendingEntity() })
                        results
                    }
                emit(Resource.Success(trending))
            } catch (throwable: Throwable) {
                Resource.Error(throwable.localizedMessage!!, throwable)
            }
        }
}