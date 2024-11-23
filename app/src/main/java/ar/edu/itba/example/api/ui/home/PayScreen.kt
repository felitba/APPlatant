package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.example.api.MyApplication
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(
    LocalContext.current.applicationContext as MyApplication
))
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var cvu by remember { mutableStateOf("") }
    var valueToPay by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.pay),
                        fontSize = 40.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                actions = {
                    IconButton(
                        onClick = {/*IR AL PERFIL DE USARIO O ABRIR UN MENU*/ },
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = stringResource(id = R.string.profile)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp) // Adjust the padding as needed
            ) {
                Text(
                    text = stringResource(id = R.string.metodos_de_pago),
                    fontSize = 30.sp
                )
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 12.dp),
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 300.dp, height = 150.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        )) {
                        Balance(balance = "\$${uiState.walletDetail?.balance ?: "0"}")
                    }
                    uiState.cards?.forEach { card ->
                        Cards(card)
                    }
                }
                Text(
                    text = stringResource(id = R.string.quantity),
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Start).padding(top = 8.dp, start = 8.dp)
                )
                OutlinedTextField(
                    value = valueToPay,
                    onValueChange = { valueToPay=it },
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = stringResource(id = R.string.quantity)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                )
                HorizontalDivider(
                    modifier = Modifier.padding(top = 32.dp),
                    color = Color.Gray.copy(alpha = 0f)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = cvu,
                        onValueChange = { cvu=it },
                        modifier = Modifier.padding(start = 8.dp),
                        placeholder = { Text(stringResource(id = R.string.cvu)) },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                    )
                    IconButton(
                        onClick = { /*poder escanear QR*/ },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = stringResource(id = R.string.qr)
                        )
                    }
                }
            }
            ElevatedButton(
                onClick = { /*Pagar*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                enabled = valueToPay.isNotEmpty() && cvu.isNotEmpty()
            ) {
                Text(
                    text = stringResource(id = R.string.pay),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Balance(balance: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.balance_amount),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Start).padding(top = 8.dp, start = 8.dp)
        )
        Text(
            text = balance,
            fontSize = 25.sp,
            modifier = Modifier.padding(top=40.dp)
        )
    }
}


@Composable
fun Cards(tarjeta: Card) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 300.dp, height = 150.dp), //Revisar si esta bien
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tarjeta.number,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.exp_date) +" " + tarjeta.expirationDate,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = tarjeta.fullName,
                    fontSize = 10.sp
                )
                Text(
                    text = tarjeta.type.toString(),
                    fontSize = 10.sp
                )
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
fun PayScreenPreview() {
    PayScreen()
}
