package ar.edu.itba.example.api.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.PreviewScreenSizes

@Composable
fun AppLogo() {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        with(adaptiveInfo) {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                Image(
                    painter = painterResource(R.drawable.platant),
                    contentDescription = stringResource(R.string.app_name),
                    Modifier.size(dimensionResource(R.dimen.image_medium))
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.platant),
                    contentDescription = stringResource(R.string.app_name),
                    Modifier.size(dimensionResource(R.dimen.image_large))
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


@PreviewScreenSizes
@Composable
fun AppLogoPreview() {
    AppLogo()
}