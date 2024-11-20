package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun AllRoute(viewModel: AllViewModel) {
    val busStops by viewModel.filteredStops.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    AllScreen(
        modifier = Modifier,
        displayedStops = busStops,
        setFavourite = viewModel::setFavourite,
        updateQuery = viewModel::updateSearchQuery,
        query = query
    )
}