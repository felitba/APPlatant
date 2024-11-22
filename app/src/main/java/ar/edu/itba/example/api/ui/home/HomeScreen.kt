package ar.edu.itba.example.api.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.example.api.MyApplication
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.components.ActionButton

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
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


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.balance_amount),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    fontSize = 18.sp
                )
                if (uiState.walletDetail != null) {
                    //TODO: deberia estar en una componente.
                    if (uiState.showBalance) {
                        Text(
                            text = "\$${uiState.walletDetail?.balance ?: "Unknown"}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            fontSize = 18.sp
                        )
                        ActionButton(
                            resId = R.string.change_balance,
                            onClick = {
                                viewModel.changeBalanceView()
                            },
                            // TODO: por alguna razon, no se muestra el icono.
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.KeyboardArrowUp,
                                    contentDescription = stringResource(id = R.string.balance_amount),
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                )
                            }
                        )
                    } else {
                        Text(
                            text = "\$*****.**",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),

                            )
                        ActionButton(
                            resId = R.string.change_balance,
                            onClick = {
                                viewModel.changeBalanceView()
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = stringResource(id = R.string.balance_amount),
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