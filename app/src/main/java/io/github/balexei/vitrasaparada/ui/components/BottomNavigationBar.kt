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
import io.github.balexei.vitrasaparada.ui.Destinations

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
        label = { Text(text = "Favourites") },
        icon = { Icon(Icons.Filled.Star, "") },
        onClick = navigateToFavourites
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.NEARBY,
        label = { Text(text = "Nearby") },
        icon = { Icon(Icons.Filled.LocationOn, "") },
        onClick = navigateToNearby
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.ALL,
        label = { Text(text = "All") },
        icon = { Icon(Icons.Filled.AddCircle, "") },
        onClick = navigateToAll
    )

    NavigationBarItem(
        selected = currentRoute == Destinations.ABOUT,
        label = { Text(text = "About") },
        icon = { Icon(Icons.Filled.Info, "") },
        onClick = navigateToAbout
    )


}