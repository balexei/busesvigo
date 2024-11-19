package io.github.balexei.vitrasaparada.data.model

import io.github.balexei.vitrasaparada.data.busstop.source.local.LocalBusStop
import io.github.balexei.vitrasaparada.data.busstop.source.network.NetworkBusStop

fun LocalBusStop.toExternal() = BusStop(
    id = id,
    name = stopName,
    routes = routes.split(","),
    stopLocation = Position(latitude = latitude, longitude = longitude),
    alias = alias,
    isFavourite = isFavourite
)

fun List<LocalBusStop>.toExternal() = map(LocalBusStop::toExternal)

fun NetworkBusStop.toLocal() = LocalBusStop(
    id = id,
    stopName = stopName,
    routes = routes,
    latitude = latitude,
    longitude = longitude,
    isFavourite = false,
    alias = null
)

fun List<NetworkBusStop>.toLocal() = map(NetworkBusStop::toLocal)
