package com.example.philm.domain.use_cases.series

import androidx.paging.PagingData
import androidx.paging.map
import com.example.philm.data.network.mapper.toSeries
import com.example.philm.domain.model.series.Series
import com.example.philm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPopularSeriesUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        language: String = "en-US",
        page: Int = 1,
    ): Flow<PagingData<Series>> =
        movieRepository.getPopularSeries(language, page).map { pagingData ->
            pagingData.map {
                it.toSeries()
            }
        }
}