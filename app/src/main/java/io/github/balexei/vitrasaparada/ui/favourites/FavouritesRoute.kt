package io.github.balexei.vitrasaparada.ui.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.balexei.vitrasaparada.ui.MainViewModel

@Composable
fun FavouritesRoute(viewModel: MainViewModel) {
    val favouriteStops by viewModel.favouriteStops.collectAsState()
    FavouritesScreen(modifier = Modifier, stops = favouriteStops, setFavourite = viewModel::setFavourite)
}