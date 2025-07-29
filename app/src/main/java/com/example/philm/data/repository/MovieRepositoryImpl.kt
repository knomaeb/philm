package com.example.philm.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.philm.core.utils.TimeWindow
import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.local.entities.list.UserListEntity
import com.example.philm.data.local.entities.movie.NowPlayingMoviesEntity
import com.example.philm.data.local.entities.movie.PopularMoviesEntity
import com.example.philm.data.local.entities.movie.UpcomingMoviesEntity
import com.example.philm.data.local.entities.series.PopularSeriesEntity
import com.example.philm.data.local.entities.series.TopRatedSeriesEntity
import com.example.philm.data.network.NetworkService
import com.example.philm.data.network.dto.movie.details.MovieDetailsDto
import com.example.philm.data.network.dto.series.details.SeriesDetailsDto
import com.example.philm.data.network.dto.trending.TrendingResponse
import com.example.philm.data.network.mediator.NowPlayingMoviesRemoteMediator
import com.example.philm.data.network.mediator.PopularMoviesRemoteMediator
import com.example.philm.data.network.mediator.PopularTvRemoteMediator
import com.example.philm.data.network.mediator.TopRatedTvRemoteMediator
import com.example.philm.data.network.mediator.UpcomingMoviesRemoteMediator
import com.example.philm.data.network.mediator.UserListRemoteMediator
import com.example.philm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl(
    private val movieApi: NetworkService,
    private val database: PhilmDatabase,
) : MovieRepository {

    override suspend fun getList(
        listId: Int,
        language: String,
        page: Int,
    ): Flow<PagingData<UserListEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                UserListRemoteMediator(
                    api = movieApi,
                    database = database,
                    listId = listId,
                    language = language,
                    page = page,
                ),
            pagingSourceFactory = {
                database.userListDao().pagingSource()
            },
        ).flow

    override suspend fun getNowPlayingMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<PagingData<NowPlayingMoviesEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                NowPlayingMoviesRemoteMediator(
                    api = movieApi,
                    database = database,
                    language = language,
                    page = page,
                    region = region,
                ),
            pagingSourceFactory = {
                database.moviesDao().nowPlayingPagingSource()
            },
        ).flow

    override suspend fun getPopularMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<PagingData<PopularMoviesEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                PopularMoviesRemoteMediator(
                    api = movieApi,
                    database = database,
                    language = language,
                    page = page,
                    region = region,
                ),
            pagingSourceFactory = {
                database.moviesDao().popularPagingSource()
            },
        ).flow

    override suspend fun getUpcomingMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<PagingData<UpcomingMoviesEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                UpcomingMoviesRemoteMediator(
                    api = movieApi,
                    database = database,
                    language = language,
                    page = page,
                    region = region,
                ),
            pagingSourceFactory = {
                database.moviesDao().upcomingPagingSource()
            },
        ).flow

    override suspend fun getMovieDetails(
        movieId: Int,
        appendToResponse: String?,
        language: String,
    ): MovieDetailsDto = movieApi.getMovieDetails(movieId, appendToResponse)

    override suspend fun getTrending(
        timeWindow: TimeWindow,
        language: String,
    ): TrendingResponse = movieApi.getTrendingMovie(timeWindow.name)

    override suspend fun getPopularSeries(
        language: String,
        page: Int,
    ): Flow<PagingData<PopularSeriesEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                PopularTvRemoteMediator(
                    api = movieApi,
                    database = database,
                    language = language,
                    page = page,
                ),
            pagingSourceFactory = {
                database.seriesDao().popularPagingSource()
            },
        ).flow

    override suspend fun getTopRatedSeries(
        language: String,
        page: Int,
    ): Flow<PagingData<TopRatedSeriesEntity>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator =
                TopRatedTvRemoteMediator(
                    api = movieApi,
                    database = database,
                    language = language,
                    page = page,
                ),
            pagingSourceFactory = {
                database.seriesDao().topRatedPagingSource()
            },
        ).flow

    override suspend fun getSeriesDetails(
        seriesId: Int,
        appendToResponse: String?,
        language: String,
    ): SeriesDetailsDto = movieApi.getSeriesDetails(seriesId, appendToResponse, language)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}