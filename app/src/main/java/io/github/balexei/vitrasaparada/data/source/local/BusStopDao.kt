package io.github.balexei.vitrasaparada.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BusStopDao {
    @Query("SELECT * FROM STOPS")
    fun observeAll(): Flow<List<LocalBusStop>>

    @Query("SELECT * FROM STOPS")
    suspend fun getAll(): List<LocalBusStop>

    @Query("SELECT * FROM STOPS WHERE id = :id")
    suspend fun getById(id: String): LocalBusStop?

    @Upsert
    suspend fun upsert(task: LocalBusStop)

    @Upsert
    suspend fun upsertAll(tasks: List<LocalBusStop>)

    @Query("DELETE FROM STOPS")
    suspend fun deleteAll()
}