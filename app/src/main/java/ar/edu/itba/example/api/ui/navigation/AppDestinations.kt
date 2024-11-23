package ar.edu.itba.example.api.ui.navigation

import androidx.annotation.StringRes
import ar.edu.itba.example.api.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: Int,
    @StringRes val contentDescription: Int,
    val route: String
) {
    HOME(R.string.home, R.drawable.home, R.string.home, "home"),
    PAY(R.string.pay, R.drawable.send, R.string.pay, "pay"),
    PROFILE(R.string.profile, R.drawable.person, R.string.profile, "profile"),
    CARDS(R.string.cards, R.drawable.credit_card, R.string.cards, "cards"),
}