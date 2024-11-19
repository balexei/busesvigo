package io.github.balexei.vitrasaparada.data.busstop

import io.github.balexei.vitrasaparada.data.model.BusStop
import kotlinx.coroutines.flow.Flow

interface BusStopRepository {
    fun getBusStopsStream(): Flow<List<BusStop>>
    suspend fun getBusStops(forceUpdate: Boolean = false) : List<BusStop>
    fun getFavoritesStream(): Flow<List<BusStop>>
    suspend fun setFavorite(id: Int, favorite: Boolean)
    suspend fun initFromNetwork()
}