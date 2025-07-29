package com.example.philm.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.philm.core.utils.Resource
import com.example.philm.domain.model.movie.Movie
import com.example.philm.domain.model.series.Series
import com.example.philm.presentation.home.components.MovieCard
import com.example.philm.presentation.home.components.MovieListItem
import com.example.philm.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    navigateToTvScreen: (id: Int) -> Unit,
    navigateToMovieScreen: (id: Int) -> Unit,
    navigateToSearchScreen: () -> Unit
){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val appBarColors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Black.copy(alpha = 0.7f),
            navigationIconContentColor = ExtendedTheme.colors.neutralWhite,
            actionIconContentColor = ExtendedTheme.colors.neutralWhite,
        )
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(ExtendedTheme.colors.neutralBlack.copy(alpha = 0.5f))
            .fillMaxSize()
    )
    HomeScreenContent(
        uiState = uiState,
        modifier =
            Modifier
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .padding(top = 96.dp),
        navigateToMovieScreen = navigateToMovieScreen,
        navigateToTvScreen = navigateToTvScreen,
    )
    Column {
        TopAppBar(
            title = {},
            actions = {
                IconButton(onClick = navigateToSearchScreen) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            colors = appBarColors,
            scrollBehavior = scrollBehavior
        )
    }
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    navigateToMovieScreen: (id:Int) -> Unit,
    navigateToTvScreen: (id:Int) -> Unit
){
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        MovieCard(
            posterPath = "https://i.ibb.co/84ND7msc/how-to-train-your-dragon.webp",
            modifier = Modifier.padding(16.dp)
        )
        PopularMovies(
            items = uiState.popularMovies.collectAsLazyPagingItems(),
            onItemClick = navigateToMovieScreen,
        )
        TopRatedSeries(
            items = uiState.topRatedSeries.collectAsLazyPagingItems(),
            onItemClick = navigateToTvScreen,
        )
        PopularSeries(
            items = uiState.popularSeries.collectAsLazyPagingItems(),
            onItemClick = { /*TODO*/ },
        )
        Trending(
            uiState = uiState,
            onItemClick = { /*TODO*/ },
        )
    }
}

@Composable
private fun PopularMovies(
    items: LazyPagingItems<Movie>,
    onItemClick: (id: Int) -> Unit
){
    if (items.loadState.refresh !is LoadState.Loading && items.itemCount > 0){
        Column {
            Text(
                text = "Popular Movies",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                color = ExtendedTheme.colors.neutralWhite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType(),
                    ) { index ->
                        items[index]?.let { item ->
                            MovieListItem(
                                onClick = { item.id?.let(onItemClick) },
                                posterPath = item.posterPath,
                                modifier = Modifier.width(100.dp),
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun TopRatedSeries(
    items: LazyPagingItems<Series>,
    onItemClick: (id: Int) -> Unit
){
    if (items.loadState.refresh !is LoadState.Loading && items.itemCount > 0){
        Column {
            Text(
                text = "Top Rated Series",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                color = ExtendedTheme.colors.neutralWhite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType(),
                    ) { index ->
                        items[index]?.let { item ->
                            MovieListItem(
                                onClick = { item.id?.let(onItemClick) },
                                posterPath = item.posterPath,
                                modifier = Modifier.width(100.dp),
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun PopularSeries(
    items: LazyPagingItems<Series>,
    onItemClick: (id: Int) -> Unit
){
    if (items.loadState.refresh !is LoadState.Loading && items.itemCount > 0){
        Column {
            Text(
                text = "Popular Series",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                color = ExtendedTheme.colors.neutralWhite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType(),
                    ) { index ->
                        items[index]?.let { item ->
                            MovieListItem(
                                onClick = { item.id?.let(onItemClick) },
                                posterPath = item.posterPath,
                                modifier = Modifier.width(100.dp),
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun Trending(
    uiState: HomeUiState,
    onItemClick: (id: Int) -> Unit,
){
    if (uiState.trendingResource is Resource.Success) {
        val popularList = uiState.trendingResource.data.orEmpty()
        Column {
            Text(
                text = "Trending Movies",
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 8.dp),
                color = ExtendedTheme.colors.neutralWhite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(
                        items = popularList,
                        key = { it.id!! },
                    ) { result ->
                        MovieListItem(
                            onClick = { result.id?.let(onItemClick) },
                            posterPath = result.posterPath,
                            modifier = Modifier.width(100.dp),
                        )
                    }
                },
            )
        }
    }
}