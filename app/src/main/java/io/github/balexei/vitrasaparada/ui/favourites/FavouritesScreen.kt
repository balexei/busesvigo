package io.github.balexei.vitrasaparada.ui.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.R
import io.github.balexei.vitrasaparada.data.model.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusFavouriteStopCard
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme
import io.github.balexei.vitrasaparada.ui.util.BusStopListPreviewParameterProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritesScreen(
    stops: List<BusStop>,
    time: String,
    setFavourite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (stops.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.no_favourites_hint))
        }
        return
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = time,
                textAlign = TextAlign.Center,
            )
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
private fun FavouritesScreenPreview(@PreviewParameter(BusStopListPreviewParameterProvider::class) stops: List<BusStop>) {
    VitrasaParadaTheme {
        FavouritesScreen(stops = stops, time = "HH:mm:ss", setFavourite = { _, _ -> })
    }
}