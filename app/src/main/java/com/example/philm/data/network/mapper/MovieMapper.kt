package com.example.philm.data.network.mapper

import com.example.philm.data.local.entities.movie.MovieEntity
import com.example.philm.data.local.entities.movie.NowPlayingMoviesEntity
import com.example.philm.data.local.entities.movie.PopularMoviesEntity
import com.example.philm.data.local.entities.movie.UpcomingMoviesEntity
import com.example.philm.data.local.entities.movie.details.MovieDetailsEntity
import com.example.philm.data.local.entities.movie.details.MovieDetailsWithRecommendations
import com.example.philm.data.local.entities.movie.details.MovieRecommendationEntity
import com.example.philm.data.network.dto.movie.MovieResult
import com.example.philm.data.network.dto.movie.details.MovieDetailsDto
import com.example.philm.domain.model.movie.Movie
import com.example.philm.domain.model.movie.MovieDetails

fun MovieDetailsDto.toMovieDetails(): MovieDetails =
    MovieDetails(
        backdropPath = backdropPath,
        cast = credits?.cast?.joinToString(", ") { it.name.orEmpty() },
        directors =
            credits
                ?.crew
                ?.filter { it.job == "Director" }
                ?.joinToString(", ") { it.name.orEmpty() },
        genres = genres?.joinToString(", ") { it?.name.orEmpty() },
        id = id,
        overview = overview,
        posterPath = posterPath,
        recommendations = recommendations?.results?.map { it.toMovie() },
        releaseDate = releaseDate,
        runtime = runtime,
        title = title,
    )

fun MovieResult.toMovie(): Movie =
    Movie(
        backdropPath = backdrop_path,
        id = id,
        overview = overview,
        posterPath = poster_path,
        title = title,
    )

fun MovieResult.toPopularMoviesEntity(): PopularMoviesEntity =
    PopularMoviesEntity(
        movie =
            MovieEntity(
                backdropPath = backdrop_path,
                movieId = id,
                overview = overview,
                posterPath = poster_path,
                title = title,
            ),
    )

fun PopularMoviesEntity.toMovie(): Movie =
    Movie(
        backdropPath = movie.backdropPath,
        id = movie.movieId,
        overview = movie.overview,
        posterPath = movie.posterPath,
        title = movie.title,
    )

fun MovieResult.toUpcomingMoviesEntity(): UpcomingMoviesEntity =
    UpcomingMoviesEntity(
        movie =
            MovieEntity(
                backdropPath = backdrop_path,
                movieId = id,
                overview = overview,
                posterPath = poster_path,
                title = title,
            ),
    )

fun UpcomingMoviesEntity.toMovie(): Movie =
    Movie(
        backdropPath = movie.backdropPath,
        id = movie.movieId,
        overview = movie.overview,
        posterPath = movie.posterPath,
        title = movie.title,
    )

fun MovieResult.toNowPlayingMoviesEntity(): NowPlayingMoviesEntity =
    NowPlayingMoviesEntity(
        movie =
            MovieEntity(
                backdropPath = backdrop_path,
                movieId = id,
                overview = overview,
                posterPath = poster_path,
                title = title,
            ),
    )

fun NowPlayingMoviesEntity.toMovie(): Movie =
    Movie(
        backdropPath = movie.backdropPath,
        id = movie.movieId,
        overview = movie.overview,
        posterPath = movie.posterPath,
        title = movie.title,
    )

fun MovieDetailsWithRecommendations.toMovieDetails(): MovieDetails =
    MovieDetails(
        backdropPath = movieDetails.backdropPath,
        cast = movieDetails.cast,
        directors = movieDetails.directors,
        genres = movieDetails.genres,
        id = movieDetails.movieId,
        overview = movieDetails.overview,
        posterPath = movieDetails.posterPath,
        recommendations = recommendations.map { it.toMovie() },
        releaseDate = movieDetails.releaseDate,
        runtime = movieDetails.runtime,
        title = movieDetails.title,
    )

fun MovieRecommendationEntity.toMovie(): Movie =
    Movie(
        backdropPath = movie.backdropPath,
        id = movie.movieId,
        overview = movie.overview,
        posterPath = movie.posterPath,
        title = movie.title,
    )

fun MovieDetails.toMovieDetailsWithRecommendations(lastUpdated: Long): MovieDetailsWithRecommendations =
    MovieDetailsWithRecommendations(
        movieDetails =
            MovieDetailsEntity(
                movieId = id!!,
                backdropPath = backdropPath,
                cast = cast,
                directors = directors,
                genres = genres,
                lastUpdated = lastUpdated,
                overview = overview,
                posterPath = posterPath,
                releaseDate = releaseDate,
                runtime = runtime,
                title = title,
            ),
        recommendations = recommendations?.map { it.toRecommendationEntity() } ?: emptyList(),
    )

fun Movie.toRecommendationEntity(): MovieRecommendationEntity =
    MovieRecommendationEntity(
        recommendationId = id!!,
        movie =
            MovieEntity(
                backdropPath = backdropPath,
                movieId = id,
                overview = overview,
                posterPath = posterPath,
                title = title,
            ),
    )