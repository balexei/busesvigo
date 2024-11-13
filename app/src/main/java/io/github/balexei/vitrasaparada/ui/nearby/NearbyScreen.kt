package io.github.balexei.vitrasaparada.ui.nearby

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.R
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.data.Position
import io.github.balexei.vitrasaparada.ui.components.BusFavouriteStopCard
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme
import io.github.balexei.vitrasaparada.ui.util.BusStopListPreviewParameterProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NearbyScreen(
    currentLocation: Position?,
    stops: List<BusStop>,
    setFavourite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        stickyHeader {
            if (currentLocation != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.lat_lon, currentLocation.latitude, currentLocation.longitude),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.showing_stops_within_radius, 200),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        items(stops) {
            BusFavouriteStopCard(
                stop = it, setFavourite = setFavourite
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun NearbyScreenPreview(@PreviewParameter(BusStopListPreviewParameterProvider::class) stops: List<BusStop>) {
    VitrasaParadaTheme {
        NearbyScreen(
            currentLocation = Position(latitude = 42.17016, longitude = -8.68835),
            stops = stops,
            setFavourite = { _, _ -> },
        )
    }
}