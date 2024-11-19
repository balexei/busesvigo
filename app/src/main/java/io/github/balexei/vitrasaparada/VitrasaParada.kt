package io.github.balexei.vitrasaparada

import android.app.Application
import androidx.room.Room
import io.github.balexei.vitrasaparada.data.busstop.BusStopRepository
import io.github.balexei.vitrasaparada.data.busstop.DefaultBusStopRepository
import io.github.balexei.vitrasaparada.data.busstop.source.local.BusStopDatabase
import io.github.balexei.vitrasaparada.data.busstop.source.network.BusStopNetworkDataSource
import io.github.balexei.vitrasaparada.data.location.GmsLocationRepository
import io.github.balexei.vitrasaparada.data.location.LocationRepository
import timber.log.Timber
import timber.log.Timber.DebugTree

class VitrasaParada : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree());
        }
    }

    val localDb by lazy {
        Room.databaseBuilder(this, BusStopDatabase::class.java, "bus_stops").build()
    }

    val busStopRepository: BusStopRepository
        get() = DefaultBusStopRepository(
            networkDataSource = BusStopNetworkDataSource(),
            localDataSource = localDb.busStopDao()
        )

    val locationRepository: LocationRepository get() = GmsLocationRepository(this)
}