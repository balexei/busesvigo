package io.github.balexei.vitrasaparada.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.balexei.vitrasaparada.R
import io.github.balexei.vitrasaparada.ui.Destinations
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    navigateToFavourites: () -> Unit,
    navigateToNearby: () -> Unit,
    navigateToAll: () -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) = NavigationBar(modifier) {

    NavigationBarItem(
        selected = currentRoute == Destinations.FAVOURITES,
        label = { Text(text = stringResource(R.string.favourites)) },
        icon = { Icon(Icons.Filled.Star, null) },
        onClick = navigateToFavourites
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.NEARBY,
        label = { Text(text = stringResource(R.string.nearby)) },
        icon = { Icon(Icons.Filled.LocationOn, null) },
        onClick = navigateToNearby
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.ALL,
        label = { Text(text = stringResource(R.string.all)) },
        icon = { Icon(Icons.Filled.AddCircle, null) },
        onClick = navigateToAll
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.ABOUT,
        label = { Text(text = stringResource(R.string.about)) },
        icon = { Icon(Icons.Filled.Info, null) },
        onClick = navigateToAbout
    )
}

@PreviewLightDark
@Composable
private fun BottomNavigationBarPreview() {
    VitrasaParadaTheme {
        BottomNavigationBar(
            currentRoute = "",
            navigateToFavourites = {},
            navigateToNearby = {},
            navigateToAll = {},
            navigateToAbout = {},
        )
    }
}