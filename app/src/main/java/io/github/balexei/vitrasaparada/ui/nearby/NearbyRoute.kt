package io.github.balexei.vitrasaparada.ui.nearby

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import io.github.balexei.vitrasaparada.ui.MainViewModel
import androidx.compose.runtime.getValue


@Composable
fun NearbyRoute(viewModel: MainViewModel) {
    val currentLocation by viewModel.currentLocation.collectAsState()
    val nearbyStops by viewModel.nearbyStops.collectAsState()
    NearbyScreen(currentLocation = currentLocation, stops = nearbyStops,  setFavourite = viewModel::setFavourite,)
}