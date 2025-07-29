package com.example.philm.presentation.home.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.philm.core.utils.AppConstants
import com.example.philm.ui.theme.PhilmTheme

@Composable
fun MovieListItem(
    onClick: () -> Unit,
    posterPath: String?,
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, fromInclusive = false) ratio: Float = 2f / 3f,
){
    Card(onClick = onClick, modifier = modifier) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data("${AppConstants.TMDB_POSTER_PREFIX}$posterPath")
                    .crossfade(true)
                    .build(),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview
@Composable
private fun MovieListItemPreview() {
    PhilmTheme {
        MovieListItem(onClick = {}, posterPath = null)
    }
}