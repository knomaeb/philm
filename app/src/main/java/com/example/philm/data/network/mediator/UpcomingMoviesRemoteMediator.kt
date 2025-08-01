package com.example.philm.data.network.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.philm.core.utils.CacheUtils
import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.local.entities.RemoteKey
import com.example.philm.data.local.entities.UpdateTime
import com.example.philm.data.local.entities.movie.UpcomingMoviesEntity
import com.example.philm.data.network.NetworkService
import com.example.philm.data.network.mapper.toUpcomingMoviesEntity
import retrofit2.HttpException
import java.io.IOException

private const val UPCOMING_MOVIES_TYPE = "upcoming_movies"

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesRemoteMediator(
    private val api: NetworkService,
    private val database: PhilmDatabase,
    private val language: String,
    private val page: Int,
    private val region: String?,
    private val type: String = UPCOMING_MOVIES_TYPE,
) : RemoteMediator<Int, UpcomingMoviesEntity>() {
    private val moviesDao = database.moviesDao()
    private val remoteKeyDao = database.remoteKeyDao()
    private val updateTimeDao = database.updateTimeDao()

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = updateTimeDao.updateTimeByType(type)?.lastUpdated ?: 0
        return if (CacheUtils.isCacheValid(lastUpdated)) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UpcomingMoviesEntity>,
    ): MediatorResult {
        return try {
            val loadKey =
                when (loadType) {
                    LoadType.REFRESH -> page

                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                    LoadType.APPEND -> {
                        val remoteKey =
                            database.withTransaction {
                                remoteKeyDao.remoteKeyByType(type)
                            }

                        if (remoteKey.nextKey == null) {
                            return MediatorResult.Success(
                                endOfPaginationReached = true,
                            )
                        }

                        remoteKey.nextKey
                    }
                }

            val response =
                api.getUpcomingMovie(
                    language = language,
                    page = loadKey,
                )
            val movies = response.results?.map { it.toUpcomingMoviesEntity() }.orEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    clearLocalDatabase()
                }

                insertLocalDatabase(nextKey = response.page?.plus(1), movies = movies)
            }

            MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun clearLocalDatabase() {
        updateTimeDao.deleteByType(type)
        remoteKeyDao.deleteByType(type)
        moviesDao.upcomingClearAll()
    }

    private suspend fun insertLocalDatabase(
        nextKey: Int?,
        movies: List<UpcomingMoviesEntity>,
    ) {
        remoteKeyDao.insertOrReplace(
            RemoteKey(
                type = type,
                nextKey = nextKey,
            ),
        )
        moviesDao.upcomingInsertAll(movies)
        updateTimeDao.insertOrReplace(
            UpdateTime(
                type = type,
                lastUpdated = System.currentTimeMillis(),
            ),
        )
    }
}