package io.github.balexei.vitrasaparada.ui.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusFavouriteStopCard
import kotlinx.coroutines.delay
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FavouritesScreen(
    stops: List<BusStop>, setFavourite: (Int, Boolean) -> Unit, modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(getCurrentUtcTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentUtcTime()
            delay(1000L)
        }
    }

    Column(modifier = modifier) {
        Text(text = "Current time for Vitrasa: $currentTime")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(stops) {
                BusFavouriteStopCard(
                    stop = it, setFavourite = setFavourite
                )
            }
        }
    }
}

fun getCurrentUtcTime(): String {
    val currentTime = ZonedDateTime.now(ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return currentTime.format(formatter)
}