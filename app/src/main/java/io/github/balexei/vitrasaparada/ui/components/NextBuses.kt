package io.github.balexei.vitrasaparada.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

@Composable
fun NextBuses(
    modifier: Modifier = Modifier,
    stopId: Int,
    minRefreshInterval: Long = 30000L,
    maxRefreshInterval: Long = 60000L
) {
    val endpoint = "https://datos.vigo.org/vitrasa-parada/"
    var imageUrlWithCacheBuster by remember { mutableStateOf("$endpoint$stopId") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = stopId) {
        while (isActive) {
            imageUrlWithCacheBuster = "$endpoint$stopId?${System.currentTimeMillis()}"
            delay(Random.nextLong(minRefreshInterval, maxRefreshInterval))
        }
    }

    val painter = rememberAsyncImagePainter(
        model = imageUrlWithCacheBuster,
        onSuccess = { isLoading = false })

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Image(
                painter = painter,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Bus stop live info"
            )
        }
    }
}