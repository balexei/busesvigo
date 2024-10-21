package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusStopCard
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AllScreen(stops: List<BusStop>, modifier: Modifier = Modifier) {
    var selectedStop by remember { mutableStateOf<BusStop?>(null) }

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(stops) {
            BusStopCard(it, modifier = Modifier.clickable(onClick = {
                selectedStop = it
            }))
        }
    }
    selectedStop?.let {
        BusStopDetailsDialog(stop = it, onDismiss = { selectedStop = null })
    }
}

@Composable
fun BusStopDetailsDialog(stop: BusStop, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        BusStopCard(stop, showSchedule = true)
    }
}