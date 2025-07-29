package com.example.philm.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.philm.presentation.components.CommonButton
import com.example.philm.presentation.components.PrimaryButtonSmall
import com.example.philm.ui.theme.ExtendedTheme
import com.example.philm.ui.theme.PhilmTheme

@Composable
fun MovieCard(
    posterPath: String?,
    modifier: Modifier = Modifier
){
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, ExtendedTheme.colors.neutralBlack),
        startY = sizeImage.height.toFloat()/3,
        endY = sizeImage.height.toFloat()
    )

    OutlinedCard(modifier = modifier.aspectRatio(2f / 3f)) {

        Box{
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PrimaryButtonSmall(
                        text = "Play",
                        imageVector = Icons.Default.PlayArrow,
                        modifier = Modifier.weight(1f),
                    )
                    CommonButton(
                        text = "Details",
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        contentColor = ExtendedTheme.colors.neutralWhite,
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MovieCardPreview(){
    PhilmTheme {
        MovieCard(posterPath = "https://i.ibb.co/84ND7msc/how-to-train-your-dragon.webp")
    }
}