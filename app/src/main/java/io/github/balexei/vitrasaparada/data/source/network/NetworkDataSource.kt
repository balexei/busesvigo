package io.github.balexei.vitrasaparada.data.source.network

interface NetworkDataSource {
    suspend fun loadBusStops(): List<NetworkBusStop>
}