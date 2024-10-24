package io.github.balexei.vitrasaparada.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme

@Composable
fun BusStopCard(stop: BusStop, modifier: Modifier = Modifier, showSchedule: Boolean = false) {
    ElevatedCard(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stop.alias ?: stop.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (stop.isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(text = "Routes")
                }
                items(stop.routes) {
                    SuggestionChip(label = { Text(it) }, onClick = {})
                }
            }
            if (showSchedule) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = "https://datos.vigo.org/vitrasa-parada/${stop.id}",
                    contentDescription = "",
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun BusStopDetailedCardPreview(@PreviewParameter(BusStopPreviewParameterProvider::class) stop: BusStop) {
    VitrasaParadaTheme {
        BusStopCard(stop, showSchedule = true)
    }
}

@PreviewLightDark
@Composable
private fun BusStopSummaryCardPreview(@PreviewParameter(BusStopPreviewParameterProvider::class) stop: BusStop) {
    VitrasaParadaTheme {
        BusStopCard(stop, showSchedule = false)
    }
}