package io.github.balexei.vitrasaparada.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.balexei.vitrasaparada.VitrasaParada
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.data.BusStopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val busStopRepository: BusStopRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        checkPopulateFreshInstall()
    }

    val busStops: StateFlow<List<BusStop>> = busStopRepository.getBusStopsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun checkPopulateFreshInstall() {
        viewModelScope.launch {
            if (busStopRepository.getBusStops().isEmpty()) {
                busStopRepository.initFromNetwork()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val busStopRepository = (this[APPLICATION_KEY] as VitrasaParada).busStopRepository
                MainViewModel(
                    busStopRepository = busStopRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}