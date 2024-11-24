package ar.edu.itba.example.api.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.CardType
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme
import kotlin.random.Random

@Composable
fun AdaptiveCard(
    card: Card,
    isSelected: Boolean = false,
    onClick: (Card) -> Unit
) {
    val (color, company, iconResId) = when (card.number[0]) {
            '3' -> Triple(Color(0xFFE1CC83), "American Express", R.drawable.credit_card)//Ver pq se rompe si pongo amex
            '4' -> Triple(Color(0xFF83E1B4), "Visa", R.drawable.visa)
            '5' -> Triple(Color(0xFFE18383), "Mastercard", R.drawable.mastercard2)
            else -> Triple(Color(0xFF83B4E1), "", R.drawable.credit_card)
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 300.dp, height = 150.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = { onClick(card) }),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = card.type.toString(),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in 0..3) {
                    Text(
                        text = card.number.substring(i * 4, i * 4 + 4),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Exp Date: ${card.expirationDate}",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = card.fullName,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$company logo",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun AdaptiveCardCardPreview() {
    APIMutableStateTheme {
        val random = Random.nextInt(0, 9999)
        Row {
            AdaptiveCard(
                card = Card(
                    number = "499003140861${random.toString().padStart(4, '0')}",
                    fullName = "Christeen Mischke",
                    expirationDate = "05/28",
                    cvv = "215",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )

            AdaptiveCard(
                card = Card(
                    number = "5112345678901234",
                    fullName = "John Doe",
                    expirationDate = "11/24",
                    cvv = "303",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )

            AdaptiveCard(
                card = Card(
                    number = "341234567890123",
                    fullName = "Jane Doe",
                    expirationDate = "12/25",
                    cvv = "456",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AdaptiveCardCardRowPreview() {
    APIMutableStateTheme {
        val random = Random.nextInt(0, 9999)
        Row {
            AdaptiveCard(
                card = Card(
                    number = "499003140861${random.toString().padStart(4, '0')}",
                    fullName = "Christeen Mischke",
                    expirationDate = "05/28",
                    cvv = "215",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )
            AdaptiveCard(
                card = Card(
                    number = "5112345678901234",
                    fullName = "John Doe",
                    expirationDate = "11/24",
                    cvv = "303",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )
            AdaptiveCard(
                card = Card(
                    number = "341234567890123",
                    fullName = "Jane Doe",
                    expirationDate = "12/25",
                    cvv = "456",
                    type = CardType.CREDIT
                ),
                onClick = {}
            )
        }
    }
}
