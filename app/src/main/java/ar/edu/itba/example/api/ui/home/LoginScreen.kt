package ar.edu.itba.example.api.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.components.AppLogo
import ar.edu.itba.example.api.ui.components.LoginForm

@Composable
fun LoginScreen(
    viewModel: HomeViewModel,
    onHomeNavigate: () -> Unit,
    onRecoverPasswordNavigate: () -> Unit,
    onRegisterNavigate: () -> Unit
) {

    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
                    .background(Color.Black)
                    .padding(dimensionResource(R.dimen.large_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppLogo()
            }
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .padding(dimensionResource(R.dimen.large_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginForm(
                    viewModel = viewModel,
                    onHomeNavigate = onHomeNavigate,
                    onRecoverPasswordNavigate = onRecoverPasswordNavigate,
                    onRegisterNavigate = onRegisterNavigate
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .background(Color.Black)
                    .padding(dimensionResource(R.dimen.large_padding)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLogo()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginForm(
                    viewModel = viewModel,
                    onHomeNavigate = onHomeNavigate,
                    onRecoverPasswordNavigate = onRecoverPasswordNavigate,
                    onRegisterNavigate = onRegisterNavigate
                )
            }
        }
    }


}