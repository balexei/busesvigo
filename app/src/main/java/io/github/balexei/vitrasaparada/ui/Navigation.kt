package io.github.balexei.vitrasaparada.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object Destinations {
    const val FAVOURITES = "favourites"
    const val NEARBY = "nearby"
    const val ALL = "all"
    const val ABOUT = "about"
}

class NavigationActions(navController: NavHostController) {
    val navigateToFavourites: () -> Unit = {
        navController.navigate(Destinations.FAVOURITES) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToNearby: () -> Unit = {
        navController.navigate(Destinations.NEARBY) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAll: () -> Unit = {
        navController.navigate(Destinations.ALL) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAbout: () -> Unit = {
        navController.navigate(Destinations.ABOUT) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}