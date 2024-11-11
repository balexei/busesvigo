package io.github.balexei.vitrasaparada.data.source.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class BusStopNetworkDataSource : NetworkDataSource {
    private val stopsUrl = "https://datos.vigo.org/data/transporte/paradas.json"
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().build()

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun loadBusStops(): List<NetworkBusStop> = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(stopsUrl).build()
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw RuntimeException("Unexpected code $response")
                }
                val adapter = moshi.adapter<List<NetworkBusStop>>()
                response.body?.string()?.let { json ->
                    val busStops = adapter.fromJson(json)
                    busStops ?: emptyList()
                } ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}