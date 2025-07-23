package com.example.philm.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.philm.data.local.entities.RemoteKey

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE type = :type")
    suspend fun remoteKeyByType(type: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE type = :type")
    suspend fun deleteByType(type: String)
}