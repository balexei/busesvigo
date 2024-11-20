package io.github.balexei.vitrasaparada.ui.all

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
import io.github.balexei.vitrasaparada.data.model.BusStop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class AllViewModel(
    private val busStopRepository: BusStopRepository, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery.debounce(250)
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val filteredStops: StateFlow<List<BusStop>> =
        debouncedQuery.combine(busStopRepository.getBusStopsStream()) { query, busStops ->
            if (query.isBlank()) {
                busStops
            } else {
                busStops.filter { busStop ->
                    busStop.name.contains(query, ignoreCase = true)
                }
            }
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


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = this[APPLICATION_KEY] as VitrasaParada
                AllViewModel(
                    busStopRepository = application.busStopRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}