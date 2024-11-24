package ar.edu.itba.example.api.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.home.HomeViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginForm(
    viewModel: HomeViewModel,
    onHomeNavigate: () -> Unit,
    onRecoverPasswordNavigate: () -> Unit,
    onRegisterNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var isPasswordVisible by remember { mutableStateOf(false) }
    var wrongAttempt by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium_padding))
    ) {
        val adaptiveInfo = currentWindowAdaptiveInfo()
        val boxWidth = with(adaptiveInfo) {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                maxWidth
            } else {
                dimensionResource(R.dimen.compact_breakpoint)
            }
        }
        Box(
            modifier = Modifier
                .width(boxWidth)
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(R.string.username)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (username.isNotEmpty()) {
                            IconButton(onClick = { username = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_padding))
                )
            }
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it},
                    label = { Text(stringResource(R.string.password)) },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        val description = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_padding))

                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                ActionButton(
                    resId = R.string.login,
                    enabled = true,
                    onClick = {
                        viewModel.login(username, password)
                        if (uiState.isAuthenticated) {
                            username = ""
                            password = ""
                            isPasswordVisible = false
                            onHomeNavigate()
                        } else {
                            wrongAttempt = true
                        }
                    }
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = dimensionResource(R.dimen.small_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = dimensionResource(R.dimen.small_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.recover_password),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            onRecoverPasswordNavigate()
                        }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = dimensionResource(R.dimen.small_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.first_time),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = dimensionResource(R.dimen.small_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.signin),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            onRegisterNavigate()
                        }
                    )
                }
            }
        }
    }

    if (wrongAttempt) {
        AlertDialog(
            onDismissRequest = {
                // TODO check behaviour
            },
            title = { Text(stringResource(R.string.failed_login_title)) },
            text = { Text(stringResource(R.string.failed_login_dialog)) },
            confirmButton = {
                Button(
                    onClick = {
                        wrongAttempt = false
                        password = ""
                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            }
        )
    }
}

