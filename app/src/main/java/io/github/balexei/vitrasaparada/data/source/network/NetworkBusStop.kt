package io.github.balexei.vitrasaparada.data.source.network

import com.squareup.moshi.Json

data class NetworkBusStop(
    @Json(name = "id") val id: Int,
    @Json(name = "nombre") val stopName: String,
    @Json(name = "lineas") val routes: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)