package com.example.philm.domain.repository

import com.example.philm.data.local.entities.trending.TrendingEntity

interface TrendingLocalRepository {
    suspend fun getCachedTrending(): List<TrendingEntity>

    suspend fun isCacheValid(): Boolean

    suspend fun clearCache()

    suspend fun saveTrending(trending: List<TrendingEntity>)
}