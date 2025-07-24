package com.example.philm.domain.use_cases.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.example.philm.data.network.mapper.toMovie
import com.example.philm.domain.model.movie.Movie
import com.example.philm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNowPlayingMoviesUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ): Flow<PagingData<Movie>> =
        movieRepository.getNowPlayingMovies(language, page).map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
}