package com.example.philm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.philm.data.local.entities.movie.NowPlayingMoviesEntity
import com.example.philm.data.local.entities.movie.PopularMoviesEntity
import com.example.philm.data.local.entities.movie.UpcomingMoviesEntity

@Dao
interface MoviesDao {
    @Upsert
    suspend fun popularInsertAll(movies: List<PopularMoviesEntity>)

    @Upsert
    suspend fun upcomingInsertAll(movies: List<UpcomingMoviesEntity>)

    @Upsert
    suspend fun nowPlayingInsertAll(movies: List<NowPlayingMoviesEntity>)

    @Query("SELECT * FROM popular_movies ORDER BY id")
    fun popularPagingSource(): PagingSource<Int, PopularMoviesEntity>

    @Query("SELECT * FROM upcoming_movies ORDER BY id")
    fun upcomingPagingSource(): PagingSource<Int, UpcomingMoviesEntity>

    @Query("SELECT * FROM now_playing_movies ORDER BY id")
    fun nowPlayingPagingSource(): PagingSource<Int, NowPlayingMoviesEntity>

    @Query("DELETE FROM popular_movies")
    suspend fun popularClearAll()

    @Query("DELETE FROM upcoming_movies")
    suspend fun upcomingClearAll()

    @Query("DELETE FROM now_playing_movies")
    suspend fun nowPlayingClearAll()
}