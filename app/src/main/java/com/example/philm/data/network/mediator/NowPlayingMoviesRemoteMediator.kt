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
import com.example.philm.data.local.entities.movie.NowPlayingMoviesEntity
import com.example.philm.data.network.NetworkService
import com.example.philm.data.network.mapper.toNowPlayingMoviesEntity
import retrofit2.HttpException
import java.io.IOException

private const val NOW_PLAYING_MOVIES_TYPE = "now_playing_movies"

@OptIn(ExperimentalPagingApi::class)
class NowPlayingMoviesRemoteMediator(
    private val api: NetworkService,
    private val database: PhilmDatabase,
    private val language: String,
    private val page: Int,
    private val region: String?,
    private val type: String = NOW_PLAYING_MOVIES_TYPE,
) : RemoteMediator<Int, NowPlayingMoviesEntity>() {

    private val moviesDao = database.moviesDao()
    private val remoteKeyDao = database.remoteKeyDao()
    private val updateTimeDao = database.updateTimeDao()

    @OptIn(ExperimentalPagingApi::class)
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
        state: PagingState<Int, NowPlayingMoviesEntity>,
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
                api.getNowPlayingMovie(
                    language = language,
                    page = loadKey,
                )
            val movies = response.results?.map { it.toNowPlayingMoviesEntity() }.orEmpty()

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
        moviesDao.nowPlayingClearAll()
    }

    private suspend fun insertLocalDatabase(
        nextKey: Int?,
        movies: List<NowPlayingMoviesEntity>,
    ) {
        remoteKeyDao.insertOrReplace(
            RemoteKey(
                type = type,
                nextKey = nextKey,
            ),
        )
        moviesDao.nowPlayingInsertAll(movies)
        updateTimeDao.insertOrReplace(
            UpdateTime(
                type = type,
                lastUpdated = System.currentTimeMillis(),
            ),
        )
    }

}