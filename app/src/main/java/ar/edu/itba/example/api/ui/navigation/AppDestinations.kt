package ar.edu.itba.example.api.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
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
    FAVORITE(R.string.favorite, Icons.Default.Favorite, R.string.favorite, "favorite"),
    SHOPPING(R.string.shopping, Icons.Default.ShoppingCart, R.string.shopping, "shopping"),
    PROFILE(R.string.profile, Icons.Default.AccountBox, R.string.profile, "profile"),
    //CARDS(R.string.cards, Icons.Outlined.CreditCard, R.string.cards, "cards"),
}