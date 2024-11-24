package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var cvu by remember { mutableStateOf("") }
    var valueToPay by remember { mutableStateOf("") }
    var selectedCard by remember { mutableStateOf<Card?>(null) }
    var balance by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.pay),
                        fontSize = 40.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                ),
                actions = {
                    IconButton(
                        onClick = {/*IR AL PERFIL DE USARIO O ABRIR UN MENU*/ },
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(id = R.string.profile),
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
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
                    .verticalScroll(rememberScrollState()) // Enables scrolling for overflowing content
                    .padding(bottom = 80.dp) // Ensures bottom padding for `ElevatedButton`
            ) {
                Text(
                    text = stringResource(id = R.string.metodos_de_pago),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp),
                    color = Color.Black
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 12.dp),
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 300.dp, height = 150.dp)//            .padding(8.dp)
                            .border(
                                width = if (balance) 4.dp else 0.dp,
                                color = if (balance) Color.Blue else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color(0xFF2C2C2C)
                        ),
                    ) {
                        Box(
                            modifier = Modifier.clickable { balance = true ; selectedCard = null },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo2),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().alpha(0.3f),
                                contentScale = ContentScale.Crop
                            )
                            Balance(balance = "\$${uiState.walletDetail?.balance ?: "0"}")
                        }
                    }
                    uiState.cards?.forEach { card ->
                        Cards(
                            tarjeta = card,
                            selected = card == selectedCard,
                            onClick = { selectedCard = card; balance = false }
                        )
                    }
                }

                // Add a small spacing for clarity
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.quantity),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp, bottom = 8.dp),
                    color = Color.Black
                )

                OutlinedTextField(
                    value = valueToPay,
                    onValueChange = { valueToPay = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = stringResource(id = R.string.quantity)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text( text = "500") }
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add spacing before next element
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    OutlinedTextField(
                        value = cvu,
                        onValueChange = { cvu = it },
                        placeholder = { Text(stringResource(id = R.string.cvu)) },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f) // Allow flexibility in layout
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between the text field and icon
                    IconButton(
                        onClick = { /* Scan QR Code action */ },
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = stringResource(id = R.string.qr),
                            tint = Color.DarkGray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                onClick = { /* Pagar */ },
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
fun Cards(tarjeta: Card, selected: Boolean, onClick: () -> Unit) {
    val (color, company, iconResId) = when (tarjeta.number[0]) {
        '3' -> Triple(Color(0xFFE1CC83), "American Express", R.drawable.credit_card)//Ver pq se rompe si pongo amex
        '4' -> Triple(Color(0xFF83E1B4), "Visa", R.drawable.visa)
        '5' -> Triple(Color(0xFFE18383), "Mastercard", R.drawable.mastercard2)
        else -> Triple(Color(0xFF83B4E1), "", R.drawable.credit_card)
    }

    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 300.dp, height = 150.dp)
            .border(
                width = if (selected) 4.dp else 0.dp,
                color = if (selected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = color
        ),
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
                text = stringResource(id = R.string.exp_date) + " " + tarjeta.expirationDate,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = tarjeta.fullName,
                    fontSize = 10.sp
                )
                Column(horizontalAlignment = Alignment.End) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = stringResource(id = R.string.icono),
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = tarjeta.type.toString(),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
