package io.github.balexei.vitrasaparada.data

data class BusStop(
    val id: Int,
    val name: String,
    val routes: List<String>,
    val stopLocation: Position,
    val isFavourite: Boolean,
    val alias: String?
)

data class Position(val latitude: Double, val longitude: Double)