package io.github.balexei.vitrasaparada.data

data class BusStop(
    val id: Int,
    val name: String,
    val routes: List<String>,
    val stopLocation: BusStopLocation,
    val isFavourite: Boolean,
    val alias: String?
)

data class BusStopLocation(val latitude: Double, val longitude: Double)