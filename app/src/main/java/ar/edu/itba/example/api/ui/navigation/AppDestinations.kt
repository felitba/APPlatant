package ar.edu.itba.example.api.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.ui.graphics.vector.ImageVector
import ar.edu.itba.example.api.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
    val route: String
) {
    HOME(R.string.home, Icons.Default.Home, R.string.home, "home"),
    PAY(R.string.pay, Icons.AutoMirrored.Outlined.Send, R.string.pay, "pay"),
    PROFILE(R.string.profile, Icons.Default.AccountBox, R.string.profile, "profile"),
    CARDS(R.string.cards, Icons.Outlined.CreditCard, R.string.cards, "cards"),
    LOGIN(R.string.login, Icons.Outlined.AccountCircle , R.string.login, "login"),
}