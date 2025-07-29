package com.example.philm.domain.repository

import androidx.paging.PagingData
import com.example.philm.core.utils.TimeWindow
import com.example.philm.data.local.entities.list.UserListEntity
import com.example.philm.data.local.entities.movie.NowPlayingMoviesEntity
import com.example.philm.data.local.entities.movie.PopularMoviesEntity
import com.example.philm.data.local.entities.movie.UpcomingMoviesEntity
import com.example.philm.data.local.entities.series.PopularSeriesEntity
import com.example.philm.data.local.entities.series.TopRatedSeriesEntity
import com.example.philm.data.network.dto.movie.details.MovieDetailsDto
import com.example.philm.data.network.dto.series.details.SeriesDetailsDto
import com.example.philm.data.network.dto.trending.TrendingResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getList(
        listId: Int,
        language: String = "en-US",
        page: Int = 1,
    ): Flow<PagingData<UserListEntity>>

    // Movie Lists

    suspend fun getNowPlayingMovies(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ): Flow<PagingData<NowPlayingMoviesEntity>>

    suspend fun getPopularMovies(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ): Flow<PagingData<PopularMoviesEntity>>

    suspend fun getUpcomingMovies(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ): Flow<PagingData<UpcomingMoviesEntity>>

    // Movies

    suspend fun getMovieDetails(
        movieId: Int,
        appendToResponse: String? = null,
        language: String = "en-US",
    ): MovieDetailsDto

    // Trending

    suspend fun getTrending(
        timeWindow: TimeWindow = TimeWindow.day,
        language: String = "en-US",
    ): TrendingResponse

    // TV Series Lists

    suspend fun getPopularSeries(
        language: String = "en-US",
        page: Int = 1,
    ): Flow<PagingData<PopularSeriesEntity>>

    suspend fun getTopRatedSeries(
        language: String = "en-US",
        page: Int = 1,
    ): Flow<PagingData<TopRatedSeriesEntity>>

    // TV Series

    suspend fun getSeriesDetails(
        seriesId: Int,
        appendToResponse: String? = null,
        language: String = "en-US",
    ): SeriesDetailsDto
}