package ar.edu.itba.example.api.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import ar.edu.itba.example.api.ui.theme.AdaptiveTheme

@Composable
fun AdaptiveCard(
    @StringRes title: Int,
    @StringRes description: Int
) {
    BoxWithConstraints {
        if (maxWidth < 600.dp) {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Face, contentDescription = null)
                    Text(text = stringResource(title))
                }
            }
        } else {
            Card {
                Row(modifier = Modifier.padding(10.dp)) {
                    Column {
                        Text(stringResource(title))
                        Text(stringResource(description))
                    }
                    Icon(imageVector = Icons.Filled.Face, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun AdaptiveCardCardSmallPreview() {
    AdaptiveTheme {
        AdaptiveCard(R.string.card_title, R.string.card_description)
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AdaptiveCardCardPreview() {
    AdaptiveTheme {
        AdaptiveCard(R.string.card_title, R.string.card_description)
    }
}