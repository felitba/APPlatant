package ar.edu.itba.example.api.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.CardType
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme
import kotlin.random.Random

@Composable
fun AdaptiveCard(
    card : Card,
    onClick: (Card) -> Unit

) {
    BoxWithConstraints {
        //TODO:  adaptarlo a tablet
        if (maxWidth < 600.dp) {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                        .clickable(onClick = { onClick(card) })

                ) {
                    Column(
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Text(text = card.type.toString(),
                            fontSize = typography.bodyMedium.fontSize
                            )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ){
                            for (i in 0..3) {
                                Text(text = card.number.substring(i*4, i*4+4),
                                    fontSize = typography.bodyMedium.fontSize
                                )
                            }
                            }
                        Row(
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Text(text = "Exp Date: ",
                                fontSize = typography.bodyMedium.fontSize
                            )
                            Text(text = card.expirationDate,
                                fontSize = typography.bodyMedium.fontSize
                            )
                        }
                        Row(
                            modifier = Modifier.padding(top = 5.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            ) {
                            Text(text = card.fullName,
                                fontSize = typography.bodyMedium.fontSize
                            )
                            //TODO: cambiar por el logo de Visa, mastercard, o lo que sea.
                            Icon(imageVector = Icons.Filled.Face, contentDescription = null)
                        }

                    }

                }
            }
        } else {
            Card {
                Row(modifier = Modifier.padding(10.dp)) {
                    Column(
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Text(text = card.type.toString())
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ){
                            for (i in 0..3) {
                                Text(text = card.number.substring(i*4, i*4+4))
                            }
                        }
                        Row(
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Text(text = "Exp Date: ")
                            Text(text = card.expirationDate)
                        }
                        Row(
                            modifier = Modifier.padding(top = 5.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                        ) {
                            Text(text = card.fullName)
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
            type = CardType.CREDIT),
            onClick = {})
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AdaptiveCardCardPreview() {
    APIMutableStateTheme {
        val random = Random.nextInt(0, 9999)
        Row {
            AdaptiveCard(
                Card(
                    number = "499003140861${random.toString().padStart(4, '0')}",
                    fullName = "Christeen Mischke",
                    expirationDate = "05/28",
                    cvv = "215",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )
//            AdaptiveCard(
//                Card(
//                    number = "499003140861${random.toString().padStart(4, '0')}",
//                    fullName = "Christeen Mischke",
//                    expirationDate = "05/28",
//                    cvv = "215",
//                    type = CardType.CREDIT
//                )
//            )
//            AdaptiveCard(
//                Card(
//                    number = "499003140861${random.toString().padStart(4, '0')}",
//                    fullName = "Christeen Mischke",
//                    expirationDate = "05/28",
//                    cvv = "215",
//                    type = CardType.CREDIT
//                )
//            )
        }
    }
}