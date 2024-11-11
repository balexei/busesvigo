package io.github.balexei.vitrasaparada.data.source.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkBusStop(
    @Json(name = "id") val id: Int,
    @Json(name = "nombre") val stopName: String,
    @Json(name = "lineas") val routes: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)