package io.github.balexei.vitrasaparada.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.balexei.vitrasaparada.VitrasaParada
import io.github.balexei.vitrasaparada.data.BusStopRepository

class MainViewModel(
    private val busStopRepository: BusStopRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


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