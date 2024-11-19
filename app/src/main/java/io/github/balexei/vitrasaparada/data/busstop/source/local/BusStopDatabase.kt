package io.github.balexei.vitrasaparada.data.busstop.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalBusStop::class], version = 1, exportSchema = true)
abstract class BusStopDatabase : RoomDatabase() {
    abstract fun busStopDao(): BusStopDao
}