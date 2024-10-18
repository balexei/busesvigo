package io.github.balexei.vitrasaparada.data

import io.github.balexei.vitrasaparada.data.source.local.BusStopDao
import io.github.balexei.vitrasaparada.data.source.local.LocalBusStop
import io.github.balexei.vitrasaparada.data.source.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBusStopRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: BusStopDao,
) : BusStopRepository {
    override fun getBusStopsStream(): Flow<List<BusStop>> {
        return localDataSource.observeAll().map(List<LocalBusStop>::toExternal)
    }

    override suspend fun getBusStops(forceUpdate: Boolean): List<BusStop> {
        if (forceUpdate == true) {
            TODO("Not yet implemented")
        }
        return localDataSource.getAll().toExternal()
    }

    override fun getFavoritesStream(): Flow<List<BusStop>> {
        TODO("Not yet implemented")
    }

    override fun setFavorite(id: Int, favorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun initFromNetwork() {
        localDataSource.deleteAll()
        val stops = networkDataSource.loadBusStops().toLocal()
        localDataSource.upsertAll(stops)
    }
}

