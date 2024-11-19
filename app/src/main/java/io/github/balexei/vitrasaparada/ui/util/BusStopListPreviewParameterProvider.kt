package io.github.balexei.vitrasaparada.ui.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.balexei.vitrasaparada.data.model.BusStop
import io.github.balexei.vitrasaparada.data.model.Position

class BusStopListPreviewParameterProvider : PreviewParameterProvider<List<BusStop>> {
    override val values = sequenceOf(emptyList(), shortList)

    companion object {
        val shortList = listOf(
            BusStop(
                id = 8740,
                name = "Telecomunicacións (CUVI)",
                routes = listOf("A", "15C", "U1", "U2"),
                stopLocation = Position(latitude = 42.170123888, longitude = -8.687270393),
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
                stopLocation = Position(latitude = 42.235873545, longitude = -8.720083317),
                isFavourite = false,
                alias = "Farola"
            )
        )
    }
}