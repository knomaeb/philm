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
import com.example.philm.data.local.entities.series.TopRatedSeriesEntity
import com.example.philm.data.network.NetworkService
import com.example.philm.data.network.mapper.toTopRatedSeriesEntity
import retrofit2.HttpException
import java.io.IOException

private const val TOP_RATED_SERIES_TYPE = "top_rated_series"

@OptIn(ExperimentalPagingApi::class)
class TopRatedTvRemoteMediator(
    private val api: NetworkService,
    private val database: PhilmDatabase,
    private val language: String,
    private val page: Int,
    private val type: String = TOP_RATED_SERIES_TYPE,
) : RemoteMediator<Int, TopRatedSeriesEntity>() {
    private val seriesDao = database.seriesDao()
    private val remoteKeyDao = database.remoteKeyDao()
    private val updateTimeDao = database.updateTimeDao()

    override suspend fun initialize(): RemoteMediator.InitializeAction {
        val lastUpdated = updateTimeDao.updateTimeByType(type)?.lastUpdated ?: 0
        return if (CacheUtils.isCacheValid(lastUpdated)) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopRatedSeriesEntity>,
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
                api.getTopRatedSeries(
                    language = language,
                    page = loadKey,
                )
            val series = response.results?.map { it.toTopRatedSeriesEntity() }.orEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    clearLocalDatabase()
                }

                insertLocalDatabase(nextKey = response.page?.plus(1), series = series)
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
        seriesDao.topRatedClearAll()
    }

    private suspend fun insertLocalDatabase(
        nextKey: Int?,
        series: List<TopRatedSeriesEntity>,
    ) {
        remoteKeyDao.insertOrReplace(
            RemoteKey(
                type = type,
                nextKey = nextKey,
            ),
        )
        seriesDao.topRatedInsertAll(series)
        updateTimeDao.insertOrReplace(
            UpdateTime(
                type = type,
                lastUpdated = System.currentTimeMillis(),
            ),
        )
    }
}