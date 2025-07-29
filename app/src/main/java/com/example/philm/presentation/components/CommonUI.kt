package com.example.philm.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBackButton(
    onBackClick: () -> Unit,
    modifier : Modifier = Modifier,
){
        IconButton(onClick = { onBackClick()}) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = modifier,
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchButton(
    onSearchClick: () -> Unit,
    modifier : Modifier = Modifier,
){
    IconButton(onClick = { onSearchClick()}) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesButton(
    onTvClick: () -> Unit,
    modifier : Modifier = Modifier,
){
    IconButton(onClick = { onTvClick()}) {
        Icon(
            imageVector = Icons.Default.LiveTv,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeButton(
    onHomeClick: () -> Unit,
    modifier : Modifier = Modifier,
){
    IconButton(onClick = { onHomeClick()}) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareButton(
    modifier : Modifier = Modifier,
){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListButton(
    modifier : Modifier = Modifier,
){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = modifier,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonsPreview(){
    Column {
        NavigationBackButton(onBackClick = {})
        SearchButton(onSearchClick = {})
        TvSeriesButton(onTvClick = {})
        HomeButton(onHomeClick = {})
        ShareButton()
        WatchListButton()
    }

}