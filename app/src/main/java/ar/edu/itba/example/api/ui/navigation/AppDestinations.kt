package ar.edu.itba.example.api.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import ar.edu.itba.example.api.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    HOME(R.string.home, Icons.Default.Home, R.string.home),
    PAY(R.string.pay, Icons.AutoMirrored.Outlined.Send, R.string.pay),
    //TODO: cambiar este icono... no hay uno parecido a tarjeta?
    CARDS(R.string.cards, Icons.Outlined.MailOutline, R.string.cards),
    PROFILE(R.string.profile, Icons.Default.AccountBox, R.string.profile),
}