package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.data.model.BusStop
import io.github.balexei.vitrasaparada.ui.components.BusAllStopCard
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme
import io.github.balexei.vitrasaparada.ui.util.BusStopListPreviewParameterProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllScreen(
    displayedStops: List<BusStop>,
    setFavourite: (Int, Boolean) -> Unit,
    query: String,
    updateQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        stickyHeader {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                value = query,
                onValueChange = updateQuery,
                label = { Text("Search bus stops") },
            )
        }
        items(displayedStops) {
            BusAllStopCard(
                stop = it,
                setFavourite = setFavourite,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AllScreenPreview(@PreviewParameter(BusStopListPreviewParameterProvider::class) stops: List<BusStop>) {
    VitrasaParadaTheme {
        AllScreen(
            displayedStops = stops,
            setFavourite = { _, _ -> },
            query = "query",
            updateQuery = {})
    }
}