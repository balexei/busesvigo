package io.github.balexei.vitrasaparada.ui.nearby

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.balexei.vitrasaparada.data.Position

@Composable
fun NearbyScreen(currentLocation: Position?) {
    if (currentLocation == null) {
        Text("Location not available")
    } else {
        Text("Current location LAT: ${currentLocation.latitude} LON: ${currentLocation.longitude}")
    }
}