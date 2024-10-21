package io.github.balexei.vitrasaparada.ui.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.balexei.vitrasaparada.ui.MainViewModel

@Composable
fun AllRoute(viewModel: MainViewModel) {
    val busStops by viewModel.busStops.collectAsState()
    AllScreen(busStops)
}