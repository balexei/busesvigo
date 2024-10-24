package io.github.balexei.vitrasaparada

import android.app.Application
import androidx.room.Room
import io.github.balexei.vitrasaparada.data.BusStopRepository
import io.github.balexei.vitrasaparada.data.DefaultBusStopRepository
import io.github.balexei.vitrasaparada.data.source.local.BusStopDatabase
import io.github.balexei.vitrasaparada.data.source.network.BusStopNetworkDataSource
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
}