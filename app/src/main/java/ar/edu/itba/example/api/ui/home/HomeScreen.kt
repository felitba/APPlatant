package ar.edu.itba.example.api.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.components.ActionButton
import ar.edu.itba.example.api.ui.components.MovementListItem
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPayNavigate: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.align(alignment = Alignment.End)
        ) {
            if (uiState.isAuthenticated) {
                viewModel.getCurrentUser()

                Column( modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    ActionButton(
                        resId = R.string.logout,
                        onClick = {
                            viewModel.logOutMessageDisplays()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = stringResource(id = R.string.logout),
                            )
                        }
                    )
                    Text( text = "Hi ${uiState.currentUser?.firstName} ${uiState.currentUser?.lastName}",
                        color = colorScheme.secondary,
                        fontSize = 16.sp,
                    )
                }
            }

            if (uiState.aboutToLogOut) {
                viewModel.getCurrentUser()
                Dialog(onDismissRequest = { viewModel.logOutMessageDisplays() }) {
                    Surface(
                        shape = shapes.medium,
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.confirm_logout)+ " "+ uiState.currentUser?.firstName + " " + uiState.currentUser?.lastName + "?",
                                style = typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                            )
                            Row{
                                Button(onClick = { viewModel.logOutMessageDisplays()},
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Text(stringResource(id = R.string.cancel),
                                    )
                                }
                                Button(onClick = {
                                    viewModel.logOutMessageDisplays()
                                    viewModel.logout()
                                }) {
                                    Text(stringResource(id = R.string.confirm))
                                }
                            }
                        }
                    }
                }
            }
        }


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.balance_amount),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .background(colorScheme.onBackground),
                    fontSize = 24.sp,
                    color = colorScheme.secondary
                )
                Row {
                    if (uiState.walletDetail != null) {
                        if (uiState.showBalance) {
                            Text(
                                text = "\$${uiState.walletDetail?.balance ?: "Unknown"}",
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    ),
                                fontSize = 36.sp,
                                color = colorScheme.secondary
                            )
                            ActionButton(
                                resId = R.string.change_balance,
                                onClick = {
                                    viewModel.changeBalanceView()
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Visibility,
                                        contentDescription = stringResource(id = R.string.balance_amount),
                                        modifier = Modifier
                                            .padding(end = 16.dp, bottom = 16.dp),
                                    )
                                }
                            )
                        } else {
                            Text(
                                text = "\$*****.**",
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    ),
                                fontSize = 36.sp,
                                color = colorScheme.secondary

                            )
                            ActionButton(
                                resId = R.string.change_balance,
                                onClick = {
                                    viewModel.changeBalanceView()
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.VisibilityOff,
                                        contentDescription = stringResource(id = R.string.balance_amount),
                                        modifier = Modifier
                                            .padding(
                                                end = 16.dp, bottom = 16.dp
                                            ),
                                    )
                                }
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorScheme.background)
                ) {
                    var depositAmount by remember { mutableStateOf("") }
                    ActionButton(
                        resId = R.string.deposit,
                        onClick = {
                            viewModel.changeIsWritingAmount()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = stringResource(id = R.string.deposit_money),
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp, start = 16.dp, end = 16.dp
                                    ),
                            )
                        },
                        displayText = true
                    )
                    if (uiState.isWriting) {
                        AlertDialog(
                            onDismissRequest = { viewModel.changeIsWritingAmount() },
                            title = {
                                Text(
                                    text = stringResource(id = R.string.enter_amount),
                                    fontSize = 24.sp,
                                    )
                            },
                            text = {
                                Column {
                                    TextField(
                                        value = depositAmount,
                                        onValueChange = { depositAmount = it },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        val amount = depositAmount.toDoubleOrNull()
                                        if (amount != null && amount > 0) {
                                            viewModel.deposit(amount)
                                            viewModel.changeIsWritingAmount()
                                            depositAmount = ""
                                        } else {
                                            viewModel.changeErrorMessage()
                                            Log.d(
                                                "DepositScreen",
                                                "Invalid deposit amount: $depositAmount"
                                            )
                                        }
                                    }
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.confirm),
                                        fontSize = typography.bodySmall.fontSize
                                    )
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { viewModel.changeIsWritingAmount() }
                                ) {
                                    Text(text = stringResource(id = R.string.cancel))
                                }
                            })
                    }

                    if (uiState.errorMessage) {
                        Dialog(onDismissRequest = { viewModel.changeErrorMessage() }) {
                            Surface(
                                shape = shapes.medium,
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.error_invalid_amount),
                                        style = typography.bodyMedium,
                                        color = colorScheme.error,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Button(onClick = { viewModel.changeErrorMessage() }) {
                                        Text(text = "OK")
                                    }
                                }
                            }
                        }
                    }

                    ActionButton(
                        resId = R.string.pay,
                        onClick = {
                            onPayNavigate()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Send,
                                contentDescription = stringResource(id = R.string.pay),
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp, start = 16.dp, end = 16.dp
                                    ),
                            )
                        },
                        displayText = true
                    )
                    ActionButton(
                        resId = R.string.charge,
                        onClick = {
                            //TODO: definir si vamos a implementar cobrar o no.
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.charge),
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp, start = 16.dp, end = 16.dp
                                    ),
                            )
                        },
                        displayText = true
                    )
                }

                if (uiState.payments != null) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        uiState.payments!!.forEach { payment ->
                            val desc = if (payment.description == null) {
                                ""
                            } else {
                                payment.description.toString()
                            }
                            MovementListItem(payment.amount, desc)
                        }
                    }
                }
            }
            if (uiState.error != null) {
                Text(
                    text = "${uiState.error!!.code} - ${uiState.error!!.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }