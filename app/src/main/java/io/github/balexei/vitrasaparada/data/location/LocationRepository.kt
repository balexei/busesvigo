package io.github.balexei.vitrasaparada.data.location

import io.github.balexei.vitrasaparada.data.model.Position
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {
    fun getLocationFlow(): StateFlow<Position?>
    fun startLocationUpdates(): LocationStatus
    fun stopLocationUpdates()
}