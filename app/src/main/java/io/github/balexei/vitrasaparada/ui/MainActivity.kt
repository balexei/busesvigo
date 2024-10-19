package io.github.balexei.vitrasaparada.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.balexei.vitrasaparada.ui.components.BottomNavigationBar
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitrasaParadaTheme {
                val navController = rememberNavController()
                val navigationActions = remember(navController) {
                    NavigationActions(navController)
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute =
                    navBackStackEntry?.destination?.route ?: Destinations.FAVOURITES
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            navigateToFavourites = navigationActions.navigateToFavourites,
                            navigateToNearby = navigationActions.navigateToNearby,
                            navigateToAll = navigationActions.navigateToAll,
                            navigateToAbout = navigationActions.navigateToAbout,
                        )
                    }
                ) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
