package ar.edu.itba.example.api.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R
import java.text.NumberFormat
import java.util.*

@Composable
fun MovementListItem(
    amount: Float,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.small_padding)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Person icon
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Time and description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Amount
            val isPositive = amount >= 0
            val amountColor = if (isPositive) Color.Green else Color.Red
            val amountPrefix = if (isPositive) "+" else "-"
            val formattedAmount = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(kotlin.math.abs(amount))

            Text(
                text = "$amountPrefix$formattedAmount",
                color = amountColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}