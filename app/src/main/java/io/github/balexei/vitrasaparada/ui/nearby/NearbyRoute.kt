package io.github.balexei.vitrasaparada.ui.nearby

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.github.balexei.vitrasaparada.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NearbyRoute(viewModel: NearbyViewModel) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }
    DisposableEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            viewModel.startLocationUpdates()
        }
        onDispose {
            viewModel.stopLocationUpdates()
        }
    }

    when {
        permissionState.status.isGranted -> {
            val currentLocation by viewModel.currentLocation.collectAsState()
            val nearbyStops by viewModel.nearbyStops.collectAsState()
            NearbyScreen(
                currentLocation = currentLocation,
                stops = nearbyStops,
                setFavourite = viewModel::setFavourite,
            )
        }

        permissionState.status.shouldShowRationale -> {
            PermissionRequiredScreen(message = stringResource(id = R.string.location_rationale_message),
                onRequestPermission = { permissionState.launchPermissionRequest() })
        }

        else -> {
            PermissionRequiredScreen(
                message = stringResource(id = R.string.location_required_message),
                onRequestPermission = { permissionState.launchPermissionRequest() },
                showSettingsButton = true
            )
        }
    }
}