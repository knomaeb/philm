package com.example.philm.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.philm.domain.use_cases.movie.GetNowPlayingMoviesUseCase
import com.example.philm.domain.use_cases.movie.GetPopularMoviesUseCase
import com.example.philm.domain.use_cases.movie.GetUpcomingMoviesUseCase
import com.example.philm.domain.use_cases.series.GetPopularSeriesUseCase
import com.example.philm.domain.use_cases.series.GetTopRatedSeriesUseCase
import com.example.philm.domain.use_cases.trending.GetTrendingUseCase
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getPopularMovies()
        getTrending()
        getPopularSeries()
        getTopRatedSeries()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.popularMovies.value = it
                }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.nowPlayingMovies.value = it
                }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.upcomingMovies.value = it
                }
        }
    }

    private fun getTrending() {
        getTrendingUseCase()
            .onEach { resource ->
                uiState = uiState.copy(trendingResource = resource)
            }.launchIn(viewModelScope)
    }

    private fun getPopularSeries() {
        viewModelScope.launch {
            getPopularSeriesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.popularSeries.value = it
                }
        }
    }

    private fun getTopRatedSeries() {
        viewModelScope.launch {
            getTopRatedSeriesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.topRatedSeries.value = it
                }
        }
    }
}