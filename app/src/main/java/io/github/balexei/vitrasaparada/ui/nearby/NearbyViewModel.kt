package io.github.balexei.vitrasaparada.ui.nearby

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.balexei.vitrasaparada.VitrasaParada
import io.github.balexei.vitrasaparada.data.busstop.BusStopRepository
import io.github.balexei.vitrasaparada.data.location.LocationRepository
import io.github.balexei.vitrasaparada.data.model.BusStop
import io.github.balexei.vitrasaparada.data.model.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class NearbyViewModel(
    private val busStopRepository: BusStopRepository,
    private val locationRepository: LocationRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        attachLocationListeners()
    }

    private val _currentLocation = MutableStateFlow<Position?>(null)
    val currentLocation: StateFlow<Position?> = _currentLocation.asStateFlow()

    val nearbyStops: StateFlow<List<BusStop>> =
        currentLocation.combine(busStopRepository.getBusStopsStream()) { location, stops ->
            location?.let {
                filterNearbyStops(stops, it)
            } ?: emptyList()
        }.flowOn(Dispatchers.Default).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setFavourite(id: Int, value: Boolean) {
        viewModelScope.launch {
            Timber.d("Setting stop id $id favourite to $value")
            busStopRepository.setFavorite(id, value)
        }
    }

    fun startLocationUpdates() {
        locationRepository.startLocationUpdates()
    }

    fun stopLocationUpdates() {
        locationRepository.stopLocationUpdates()
    }

    private fun filterNearbyStops(
        stops: List<BusStop>,
        position: Position,
    ): List<BusStop> {
        return stops.filter {
            val results = FloatArray(1)
            Location.distanceBetween(
                position.latitude,
                position.longitude,
                it.stopLocation.latitude,
                it.stopLocation.longitude,
                results
            )
            results[0] < 200
        }
    }

    private fun attachLocationListeners() {
        viewModelScope.launch {
            locationRepository.getLocationFlow()
                .map { it?.let { Position(it.latitude, it.longitude) } }
                .collect { position ->
                    if (position != null) {
                        _currentLocation.value = position
                    }
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = this[APPLICATION_KEY] as VitrasaParada
                NearbyViewModel(
                    busStopRepository = application.busStopRepository,
                    locationRepository = application.locationRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}