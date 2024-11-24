package ar.edu.itba.example.api.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.itba.example.api.ui.home.PayScreen
import ar.edu.itba.example.api.ui.home.HomeScreen
import ar.edu.itba.example.api.ui.home.HomeViewModel
import ar.edu.itba.example.api.ui.home.ProfileScreen
import ar.edu.itba.example.api.ui.home.CardsScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen(viewModel)
        }
        composable(route = AppDestinations.CARDS.route) {
            CardsScreen(viewModel)
        }
        composable(route = AppDestinations.PROFILE.route) {
            ProfileScreen(viewModel)
        }
        composable(route = AppDestinations.PAY.route) {
            PayScreen(viewModel)
        }
    }
}