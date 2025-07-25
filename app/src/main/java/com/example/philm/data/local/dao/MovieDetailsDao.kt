package com.example.philm.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.philm.data.local.entities.movie.details.MovieDetailsEntity
import com.example.philm.data.local.entities.movie.details.MovieDetailsWithRecommendations
import com.example.philm.data.local.entities.movie.details.MovieRecommendationCrossRef
import com.example.philm.data.local.entities.movie.details.MovieRecommendationEntity

@Dao
interface MovieDetailsDao {
    @Upsert
    suspend fun upsertMovieDetails(movieDetails: MovieDetailsEntity)

    @Upsert
    suspend fun upsertRecommendations(recommendations: List<MovieRecommendationEntity>)

    @Upsert
    suspend fun upsertMovieRecommendationCrossRef(movieRecommendationCrossRef: List<MovieRecommendationCrossRef>)

    @Query("DELETE FROM movie_details WHERE movieId = :id")
    suspend fun deleteMovieDetails(id: Int)

    @Query("DELETE FROM MovieRecommendationCrossRef WHERE movieId = :movieId")
    suspend fun deleteMovieRecommendationCrossRef(movieId: Int)

    @Transaction
    @Query("SELECT * FROM movie_details WHERE movieId = :id")
    suspend fun getMovieDetailsWithRecommendations(id: Int): MovieDetailsWithRecommendations?

    @Transaction
    suspend fun upsertMovieDetailsWithRecommendations(
        movieDetails: MovieDetailsEntity,
        recommendations: List<MovieRecommendationEntity>,
        movieRecommendationCrossRefs: List<MovieRecommendationCrossRef>,
    ) {
        upsertMovieDetails(movieDetails)
        upsertRecommendations(recommendations)
        upsertMovieRecommendationCrossRef(movieRecommendationCrossRefs)
    }

    @Transaction
    suspend fun deleteMovieDetailsWithCrossRefs(id: Int) {
        deleteMovieRecommendationCrossRef(id)
        deleteMovieDetails(id)
    }

}