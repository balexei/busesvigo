package io.github.balexei.vitrasaparada.ui.favourites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.balexei.vitrasaparada.ui.MainViewModel
import kotlinx.coroutines.delay
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FavouritesRoute(viewModel: MainViewModel) {
    val favouriteStops by viewModel.favouriteStops.collectAsState()
    var currentTime by remember { mutableStateOf(getCurrentUtcTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentUtcTime()
            delay(1000L)
        }
    }

    FavouritesScreen(
        modifier = Modifier.fillMaxSize(),
        stops = favouriteStops,
        setFavourite = viewModel::setFavourite,
        time = currentTime
    )
}

fun getCurrentUtcTime(): String {
    val currentTime = ZonedDateTime.now(ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return currentTime.format(formatter)
}