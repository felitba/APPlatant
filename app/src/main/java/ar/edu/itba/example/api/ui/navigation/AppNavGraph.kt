package ar.edu.itba.example.api.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.itba.example.api.ui.home.PayScreen
import ar.edu.itba.example.api.ui.home.HomeScreen
import ar.edu.itba.example.api.ui.home.HomeViewModel
import ar.edu.itba.example.api.ui.home.ProfileScreen
import ar.edu.itba.example.api.ui.home.CardsScreen
import ar.edu.itba.example.api.ui.home.LoginScreen
import ar.edu.itba.example.api.ui.home.RegisterView

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (uiState.isAuthenticated) {
            AppDestinations.HOME.route
        } else  {
            AppDestinations.LOGIN.route
        }
    ) {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen(viewModel = viewModel,
                onPayNavigate = {
                    navController.navigate(AppDestinations.PAY.route) {
                        popUpTo(AppDestinations.HOME.route) {
                            inclusive = false
                        }
                    }
            })
        }
        composable(route = AppDestinations.CARDS.route) {
            CardsScreen(viewModel = viewModel)
        }
        composable(route = AppDestinations.PROFILE.route) {
            ProfileScreen(viewModel)
        }
        composable(route = AppDestinations.PAY.route) {
            PayScreen(viewModel)
        }
        composable(route = AppDestinations.LOGIN.route) {
            LoginScreen(
                viewModel = viewModel,
                onHomeNavigate = {
                    navController.navigate(AppDestinations.HOME.route) {
                        popUpTo(AppDestinations.LOGIN.route) { inclusive = true }
                    }
                },
                onRecoverPasswordNavigate = {},
                onRegisterNavigate = {
                    navController.navigate(AppDestinations.REGISTER.route)
                }
            )
        }
        composable(route = AppDestinations.REGISTER.route) {
            RegisterView(
                viewModel = viewModel,
                onCancelNavigate = {
                    navController.navigate(AppDestinations.LOGIN.route) {
                        popUpTo(AppDestinations.LOGIN.route) { inclusive = true }
                    }
                },
                onSubmitNavigate = {
                    navController.navigate(AppDestinations.LOGIN.route) {
                        popUpTo(AppDestinations.LOGIN.route) { inclusive = true }
                    }
                }
            )
        }
    }
}