package ar.edu.itba.example.api.ui.home

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.itba.example.api.ui.AppPlatant
import ar.edu.itba.example.api.ui.PreviewLocales
import ar.edu.itba.example.api.ui.PreviewScreenSizes
import ar.edu.itba.example.api.ui.components.LoginForm
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme

@Composable
fun LoginScreen(
    viewModel: HomeViewModel,
    modifier: Modifier,
    onHomeNavigate: () -> Unit,
    onRecoverPasswordNavigate: () -> Unit,
    onRegisterNavigate: () -> Unit
) {
    /*val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

    } else {

    }*/

    LoginForm(
        viewModel = viewModel,
        onHomeNavigate = onHomeNavigate,
        onRegisterNavigate = onRegisterNavigate,
        onRecoverPasswordNavigate = onRecoverPasswordNavigate
    )

}