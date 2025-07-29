package com.example.philm.presentation.home

import androidx.paging.PagingData
import com.example.philm.core.utils.Resource
import com.example.philm.domain.model.movie.Movie
import com.example.philm.domain.model.series.Series
import com.example.philm.domain.model.trending.Trending
import kotlinx.coroutines.flow.MutableStateFlow

data class HomeUiState(
    val popularMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty()),
    val trendingResource: Resource<List<Trending>> = Resource.Empty(),
    val popularSeries: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(PagingData.empty()),
    val topRatedSeries: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(PagingData.empty()),
    val nowPlayingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty()),
    val upcomingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty()),
)
