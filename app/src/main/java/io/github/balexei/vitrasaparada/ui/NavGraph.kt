package io.github.balexei.vitrasaparada.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.balexei.vitrasaparada.ui.about.AboutRoute
import io.github.balexei.vitrasaparada.ui.all.AllRoute
import io.github.balexei.vitrasaparada.ui.favourites.FavouritesRoute
import io.github.balexei.vitrasaparada.ui.nearby.NearbyRoute

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.FAVOURITES
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Destinations.FAVOURITES
        ) { navBackStackEntry ->
            FavouritesRoute()
        }
        composable(
            route = Destinations.NEARBY
        ) { navBackStackEntry ->
            NearbyRoute()
        }
        composable(
            route = Destinations.ALL
        ) { navBackStackEntry ->
            AllRoute(viewModel)
        }
        composable(
            route = Destinations.ABOUT
        ) { navBackStackEntry ->
            AboutRoute()
        }

    }
}