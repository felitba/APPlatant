package ar.edu.itba.example.api.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
    val uiState by viewModel.uiState.collectAsState()

    var verifying by remember { mutableStateOf(false) }

    var code by remember { mutableStateOf("") }
    var errorCode by remember { mutableStateOf(false) }

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
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(dimensionResource(R.dimen.small_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LoginForm(
                        viewModel = viewModel,
                        onHomeNavigate = onHomeNavigate,
                        onRecoverPasswordNavigate = onRecoverPasswordNavigate,
                        onRegisterNavigate = onRegisterNavigate
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.verify_mail),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.clickable {
                            verifying = true
                        }
                    )
                }
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
                    .weight(0.5f),
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Text(
                    text = stringResource(R.string.verify_mail),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.clickable {
                        verifying = true
                    }
                )
            }
        }
    }

    if(verifying) {
        AlertDialog(
            onDismissRequest = {
                errorCode = false
                verifying = false
            },
            title = { Text(stringResource(R.string.verify_mail)) },
            text = {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.medium_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
                ) {
                    Text(stringResource(R.string.verify_mail_dialog))

                    OutlinedTextField(
                        value = code,
                        onValueChange = {
                            code = it
                        },
                        label = { Text(stringResource(R.string.code)) },
                        singleLine = true,
                        isError = errorCode,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (code.isNotEmpty()) {
                                IconButton(onClick = { code = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                    if (errorCode) {
                        Text(
                            text = stringResource(R.string.incorrect_code),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },



            confirmButton = {
                Button(
                    onClick = {
                        viewModel.verify(code)
                        if (uiState.error != null) {
                            errorCode = true
                        } else {
                            verifying = false
                            errorCode = false
                        }

                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            }
        )
    }
}