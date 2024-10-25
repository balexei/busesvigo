package io.github.balexei.vitrasaparada.ui.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusFavouriteStopCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritesScreen(
    stops: List<BusStop>,
    time: String,
    setFavourite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
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