package io.github.balexei.vitrasaparada.data

import io.github.balexei.vitrasaparada.data.source.local.BusStopDao
import io.github.balexei.vitrasaparada.data.source.local.LocalBusStop
import io.github.balexei.vitrasaparada.data.source.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

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

    override suspend fun setFavorite(id: Int, favourite: Boolean) {
        val stop = localDataSource.getById(id)
        if (stop != null) {
            val updatedStop = stop.copy(isFavourite = favourite)
            localDataSource.upsert(updatedStop)
            Timber.d("Stop $id favourite set to $favourite")
        } else {
            Timber.w("Attempted to set favourite a stop that does not exist (id = $id)")
        }
    }

    override suspend fun initFromNetwork() {
        localDataSource.deleteAll()
        val stops = networkDataSource.loadBusStops().toLocal()
        localDataSource.upsertAll(stops)
    }
}

