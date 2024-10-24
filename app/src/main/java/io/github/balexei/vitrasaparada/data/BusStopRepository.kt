package io.github.balexei.vitrasaparada.data

import kotlinx.coroutines.flow.Flow

interface BusStopRepository {
    fun getBusStopsStream(): Flow<List<BusStop>>
    suspend fun getBusStops(forceUpdate: Boolean = false) : List<BusStop>
    fun getFavoritesStream(): Flow<List<BusStop>>
    suspend fun setFavorite(id: Int, favorite: Boolean)
    suspend fun initFromNetwork()
}