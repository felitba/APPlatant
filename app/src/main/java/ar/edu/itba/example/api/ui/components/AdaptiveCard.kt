package ar.edu.itba.example.api.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.CardType
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme
import kotlin.random.Random

@Composable
fun AdaptiveCard(
    Card : Card,
) {
    BoxWithConstraints {
        if (maxWidth < 600.dp) {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column {
                        Text(text = Card.type.toString())
                        Text(text = Card.number)
                        Row {
                            Text(text = "Exp Date")
                            Text(text = Card.expirationDate)
                        }
                        Row {
                            Text(text = Card.fullName)
                            //TODO: cambiar por el logo de Visa, mastercard, o lo que sea.
                            Icon(imageVector = Icons.Filled.Face, contentDescription = null)
                        }

                    }

                }
            }
        } else {
            Card {
                Row(modifier = Modifier.padding(10.dp)) {
                    Column {
                        Text(text = Card.type.toString())
                        Text(text = Card.number)
                        Row {
                            Text(text = "Exp Date: ")
                            Text(text = Card.expirationDate)
                        }
                        Row {
//                            Spacer(modifier = Modifier.weight(0.5f))
                            Text(text = Card.fullName)
                            //TODO: cambiar por el logo de Visa, mastercard, o lo que sea.
                            Icon(imageVector = Icons.Filled.Face, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun AdaptiveCardCardSmallPreview() {
    APIMutableStateTheme {
        val random = Random.nextInt(0, 9999)
        AdaptiveCard(Card(number = "499003140861${random.toString().padStart(4, '0')}",
            fullName = "Christeen Mischke",
            expirationDate = "05/28",
            cvv = "215",
            type = CardType.CREDIT))
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AdaptiveCardCardPreview() {
    APIMutableStateTheme {
        val random = Random.nextInt(0, 9999)
        AdaptiveCard(Card(number = "499003140861${random.toString().padStart(4, '0')}",
            fullName = "Christeen Mischke",
            expirationDate = "05/28",
            cvv = "215",
            type = CardType.CREDIT))
    }
}