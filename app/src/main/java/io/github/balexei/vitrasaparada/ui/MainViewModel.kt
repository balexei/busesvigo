package io.github.balexei.vitrasaparada.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import io.github.balexei.vitrasaparada.VitrasaParada
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.data.BusStopRepository
import io.github.balexei.vitrasaparada.data.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val application: Application,
    private val busStopRepository: BusStopRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        checkPopulateFreshInstall()
        attachLocationListeners()
    }

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery.debounce(250)
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredStops: StateFlow<List<BusStop>> = debouncedQuery
        .combine(busStopRepository.getBusStopsStream()) { query, busStops ->
            if (query.isBlank()) {
                busStops
            } else {
                busStops.filter { busStop ->
                    busStop.name.contains(query, ignoreCase = true)
                }
            }
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favouriteStops: StateFlow<List<BusStop>> = busStopRepository.getFavoritesStream()
        .stateIn(
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

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun checkPopulateFreshInstall() {
        viewModelScope.launch {
            if (busStopRepository.getBusStops().isEmpty()) {
                Timber.d("No bus stops saved, initializing list of bus stops")
                busStopRepository.initFromNetwork()
            }
        }
    }

    private val _currentLocation = MutableStateFlow<Position?>(null)
    val currentLocation: StateFlow<Position?> = _currentLocation

    val nearbyStops: StateFlow<List<BusStop>> =
        currentLocation.combine(busStopRepository.getBusStopsStream()) { location, stops ->
            location?.let {
                filterNearbyStops(stops, it)
            } ?: emptyList()
        }
            .flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

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
            delay(1L) // FIXME ñapa
            _currentLocation.subscriptionCount
                .map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) checkPermissionAndStartLocationUpdates() else stopLocationUpdates()
                }.launchIn(viewModelScope)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            location.lastLocation?.let {
                Timber.d("New location $it")
                _currentLocation.value = Position(latitude = it.latitude, longitude = it.longitude)
            }
        }
    }

    private var isTrackingLocation = false
    private val _requestLocationPermissionEvent = MutableSharedFlow<Unit>()
    val requestLocationPermissionEvent = _requestLocationPermissionEvent.asSharedFlow()

    suspend fun checkPermissionAndStartLocationUpdates() {
        Timber.d("Checking permission")
        if (ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("Emit ask for permission")
            _requestLocationPermissionEvent.emit(Unit)
        } else {
            startLocationUpdates()
        }

    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        if (!isTrackingLocation) {
            isTrackingLocation = true
            Timber.d("Start location updates")
            val locationRequest =
                LocationRequest.Builder(0L).setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setIntervalMillis(10000L).build()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun stopLocationUpdates() {
        Timber.d("Stop location updates")
        if (isTrackingLocation) {
            isTrackingLocation = false
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = this[APPLICATION_KEY] as VitrasaParada
                val busStopRepository = application.busStopRepository
                MainViewModel(
                    application = application,
                    busStopRepository = busStopRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}