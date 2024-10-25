package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.balexei.vitrasaparada.ui.MainViewModel

@Composable
fun AllRoute(viewModel: MainViewModel) {
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