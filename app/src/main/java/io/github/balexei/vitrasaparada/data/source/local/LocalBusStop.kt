package io.github.balexei.vitrasaparada.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "STOPS")
data class LocalBusStop(
    @PrimaryKey
    val id: Int,
    val stopName: String,
    val routes: String,
    val latitude: Double,
    val longitude: Double,
    val isFavourite: Boolean,
    val alias: String,
)