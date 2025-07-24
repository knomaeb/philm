package com.example.philm.data.local.entities.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_movies")
data class NowPlayingMoviesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Embedded val movie: MovieEntity,
)