package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusAllStopCard

@Composable
fun AllScreen(
    stops: List<BusStop>,
    setFavourite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(stops) {
            BusAllStopCard(
                stop = it,
                setFavourite = setFavourite,
            )
        }
    }
}