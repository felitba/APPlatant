package ar.edu.itba.example.api.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.components.ActionButton

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.align(alignment = Alignment.End)
        ) {
            //TODO: cuando loginScreen este hecha sacar esto.
            if (!uiState.isAuthenticated) {
                ActionButton(
                    resId = R.string.login,
                    onClick = {
                        viewModel.login("johndoe@email.com", "1234567890")
                    })
            } else {
                ActionButton(
                    resId = R.string.logout,
                    onClick = {
                        viewModel.logout()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(id = R.string.logout),
                        )
                    }
                )
            }
        }


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.balance_amount),
                    modifier = Modifier
                        //TODO: cambiar el color, tipografia, padding con Theme.kt.
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .background(colorScheme.onBackground),
                    fontSize = 24.sp,
                    color = colorScheme.secondary
                )
                Row{
                        if (uiState.walletDetail != null) {
                            //TODO: deberia estar en una componente.
                            if (uiState.showBalance) {
                                Text(
                                    text = "\$${uiState.walletDetail?.balance ?: "Unknown"}",
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
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
                                                .padding(top = 16.dp, start = 16.dp, end = 16.dp,bottom = 16.dp),
                                        )
                                    }
                                )
                            } else {
                                Text(
                                    text = "\$*****.**",
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp,bottom = 16.dp),
                                    //TODO: cambiar usando font.kt
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
                                                    top = 16.dp, start = 16.dp, end = 16.dp,bottom = 16.dp
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
                        resId = R.string.deposit_money,
                        onClick = {
                            //TODO: hacerlo dinamico.
                            viewModel.changeIsWritingAmount()
//                            viewModel.deposit(100.00)
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
                        }
                    )
                    if (uiState.isWriting) {
                        AlertDialog(
                            onDismissRequest = { viewModel.changeIsWritingAmount() },
                            title = {
                                Text(text = stringResource(id = R.string.enter_amount))
                            },
                            text = {
                                Column {
                                    TextField(
                                        value = depositAmount,
                                        onValueChange = { depositAmount = it },
                                        label = { Text(stringResource(id = R.string.enter_amount)) },
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
                                            // Handle invalid input, e.g., show a Snackbar or error message
                                            Log.d("DepositScreen", "Invalid deposit amount: $depositAmount")
                                        }
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.deposit_money))
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
                    ActionButton(
                        resId = R.string.pay,
                        onClick = {
                            //TODO: como deberia nevegar desde Home? HomeviewModel no tiene los permisos
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
                        }
                    )
                    //TODO: definir si vamos a implementar cobrar o no.
                    ActionButton(
                        resId = R.string.charge,
                        onClick = {
                            //
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
                        }
                    )
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

//import android.content.res.Configuration
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import ar.edu.itba.example.api.R
//import ar.edu.itba.example.api.ui.components.AdaptiveCard
//
//@Composable
//fun HomeScreen() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = stringResource(R.string.home),
//            fontSize = 30.sp
//        )
//        Spacer(modifier = Modifier.size(20.dp))
//        AdaptiveCard(R.string.card_title, R.string.card_description)
//        Spacer(modifier = Modifier.size(20.dp))
//        val configuration = LocalConfiguration.current
//        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Text(text = stringResource(R.string.landscape))
//        } else {
//            Text(text = stringResource(R.string.portrait))
//        }
//    }
//}