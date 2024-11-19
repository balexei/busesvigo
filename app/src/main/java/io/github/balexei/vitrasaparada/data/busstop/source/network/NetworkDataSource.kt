package io.github.balexei.vitrasaparada.data.busstop.source.network

interface NetworkDataSource {
    suspend fun loadBusStops(): List<NetworkBusStop>
}