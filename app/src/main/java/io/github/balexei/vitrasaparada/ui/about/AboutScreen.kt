package io.github.balexei.vitrasaparada.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.balexei.vitrasaparada.R
import io.github.balexei.vitrasaparada.ui.theme.VitrasaParadaTheme

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "App icon"
        )
        Text("Vitrasa Parada")
        Spacer(modifier = Modifier.height(32.dp))
        Text("Fuente de los datos: Ayuntamiento de Vigo")
        Text("https://datos.vigo.org")
    }
}

@PreviewLightDark
@Composable
private fun NearbyScreenPreview() {
    VitrasaParadaTheme {
        AboutScreen()
    }
}