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
import com.example.philm.data.local.entities.list.UserListEntity
import com.example.philm.data.network.NetworkService
import com.example.philm.data.network.mapper.toUserListEntity
import retrofit2.HttpException
import java.io.IOException

private const val USER_LIST_TYPE = "user_list"

@OptIn(ExperimentalPagingApi::class)
class UserListRemoteMediator(
    private val api: NetworkService,
    private val database: PhilmDatabase,
    private val listId: Int,
    private val language: String,
    private val page: Int,
    private val type: String = USER_LIST_TYPE,
) : RemoteMediator<Int, UserListEntity>() {
    private val userListDao = database.userListDao()
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
        state: PagingState<Int, UserListEntity>,
    ): RemoteMediator.MediatorResult {
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
                api.getList(
                    listId = listId,
                    language = language,
                    page = loadKey,
                )
            val userList = response.results?.map { it.toUserListEntity() }.orEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    clearLocalDatabase()
                }

                insertLocalDatabase(nextKey = response.page?.plus(1), userList = userList)
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
        userListDao.clearAll()
    }

    private suspend fun insertLocalDatabase(
        nextKey: Int?,
        userList: List<UserListEntity>,
    ) {
        remoteKeyDao.insertOrReplace(
            RemoteKey(
                type = type,
                nextKey = nextKey,
            ),
        )
        userListDao.insertAll(userList)
        updateTimeDao.insertOrReplace(
            UpdateTime(
                type = type,
                lastUpdated = System.currentTimeMillis(),
            ),
        )
    }
}