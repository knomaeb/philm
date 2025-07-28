package com.example.philm.data.repository

import com.example.philm.core.utils.CacheUtils
import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.local.entities.UpdateTime
import com.example.philm.data.local.entities.trending.TrendingEntity
import com.example.philm.domain.repository.TrendingLocalRepository

private const val TRENDING_TYPE = "trending"

class TrendingLocalRepositoryImpl(
    database: PhilmDatabase,
) : TrendingLocalRepository {
    private val updateTimeDao = database.updateTimeDao()
    private val trendingDao = database.trendingDao()

    override suspend fun getCachedTrending(): List<TrendingEntity> = trendingDao.getAll()

    override suspend fun isCacheValid(): Boolean {
        val lastUpdated = updateTimeDao.updateTimeByType(TRENDING_TYPE)?.lastUpdated ?: 0
        return CacheUtils.isCacheValid(lastUpdated)
    }

    override suspend fun clearCache() {
        updateTimeDao.deleteByType(TRENDING_TYPE)
        trendingDao.clearAll()
    }

    override suspend fun saveTrending(trending: List<TrendingEntity>) {
        updateTimeDao.insertOrReplace(
            UpdateTime(
                type = TRENDING_TYPE,
                lastUpdated = System.currentTimeMillis(),
            ),
        )
        trendingDao.insertAll(trending)
    }
}