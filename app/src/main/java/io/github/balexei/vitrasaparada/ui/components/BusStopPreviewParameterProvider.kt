package io.github.balexei.vitrasaparada.ui.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.balexei.vitrasaparada.data.BusStop
import io.github.balexei.vitrasaparada.data.BusStopLocation

class BusStopPreviewParameterProvider : PreviewParameterProvider<BusStop> {
    override val values = sequenceOf(
        BusStop(
            id = 8740,
            name = "Telecomunicacións (CUVI)",
            routes = listOf("A", "15C", "U1", "U2"),
            stopLocation = BusStopLocation(latitude = 42.170123888, longitude = -8.687270393),
            isFavourite = true,
            alias = null
        ),
        BusStop(
            id = 14264,
            name = "Rúa de Urzáiz - Príncipe",
            routes = listOf(
                "C1",
                "A",
                "4A",
                "4C",
                "5A",
                "7",
                "9B",
                "12B",
                "14",
                "15B",
                "15C",
                "16",
                "17",
                "18A",
                "18B",
                "18H",
                "24",
                "28",
                "N1",
                "N4"
            ),
            stopLocation = BusStopLocation(latitude = 42.235873545, longitude = -8.720083317),
            isFavourite = false,
            alias = "Farola"
        )
    )

}