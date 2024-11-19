package io.github.balexei.vitrasaparada.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import io.github.balexei.vitrasaparada.data.model.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class GmsLocationRepository(private val application: Application) : LocationRepository {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)
    private val currentLocation = MutableStateFlow<Position?>(null)
    private var isTrackingLocation = false

    override fun getLocationFlow(): StateFlow<Position?> = currentLocation.asStateFlow()

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates(): LocationStatus {
        Timber.d("Start location updates")
        if (!locationPermissionsAvailable()) return LocationStatus.PermissionError()
        if (!isTrackingLocation) {
            isTrackingLocation = true
            val locationRequest =
                LocationRequest.Builder(0L).setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setIntervalMillis(10000L).build()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
        return LocationStatus.Success()
    }

    override fun stopLocationUpdates() {
        Timber.d("Stop location updates")
        isTrackingLocation = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun locationPermissionsAvailable(): Boolean {
        return ActivityCompat.checkSelfPermission(
            application.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            application.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            location.lastLocation?.let {
                currentLocation.value = Position(latitude = it.latitude, longitude = it.longitude)
            }
        }
    }
}