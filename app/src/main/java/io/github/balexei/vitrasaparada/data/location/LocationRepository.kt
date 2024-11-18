package io.github.balexei.vitrasaparada.data.location

import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {
    fun getLocationFlow(): StateFlow<Location?>
    fun startLocationUpdates(): LocationStatus
    fun stopLocationUpdates()
}