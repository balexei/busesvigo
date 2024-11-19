package io.github.balexei.vitrasaparada.ui.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.balexei.vitrasaparada.data.model.BusStop

class BusStopPreviewParameterProvider : PreviewParameterProvider<BusStop> {
    override val values = BusStopListPreviewParameterProvider.shortList.asSequence()
}