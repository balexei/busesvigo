package io.github.balexei.vitrasaparada.data.model

data class BusStop(
    val id: Int,
    val name: String,
    val routes: List<String>,
    val stopLocation: Position,
    val isFavourite: Boolean,
    val alias: String?
)