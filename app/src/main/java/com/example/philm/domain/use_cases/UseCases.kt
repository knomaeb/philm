package com.example.philm.domain.use_cases

import com.example.philm.domain.use_cases.movie.GetMovieDetailsUseCase
import com.example.philm.domain.use_cases.movie.GetNowPlayingMoviesUseCase
import com.example.philm.domain.use_cases.movie.GetPopularMoviesUseCase
import com.example.philm.domain.use_cases.movie.GetUpcomingMoviesUseCase
import com.example.philm.domain.use_cases.series.GetPopularSeriesUseCase
import com.example.philm.domain.use_cases.series.GetSeriesDetailsUseCase
import com.example.philm.domain.use_cases.series.GetTopRatedSeriesUseCase
import com.example.philm.domain.use_cases.trending.GetTrendingUseCase

data class UseCases(
    // movie
    val PopularMoviesList: GetPopularMoviesUseCase,
    val NowPlayingMoviesList: GetNowPlayingMoviesUseCase,
    val UpcomingMoviesList: GetUpcomingMoviesUseCase,
    val MovieDetails: GetMovieDetailsUseCase,

    // series
    val PopularSeriesList: GetPopularSeriesUseCase,
    val TopRatedSeriesList: GetTopRatedSeriesUseCase,
    val SeriesDetails: GetSeriesDetailsUseCase,

    // trending
    val TrendingList: GetTrendingUseCase,
    )