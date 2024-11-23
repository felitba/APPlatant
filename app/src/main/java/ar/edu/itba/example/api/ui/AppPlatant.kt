package ar.edu.itba.example.api.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.itba.example.api.MyApplication
import ar.edu.itba.example.api.ui.home.HomeViewModel
import ar.edu.itba.example.api.ui.navigation.AppDestinations
import ar.edu.itba.example.api.ui.navigation.AppNavGraph
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme

@Composable
fun AppPlatant(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    APIMutableStateTheme {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val adaptiveInfo = currentWindowAdaptiveInfo()
        val customNavSuiteType = with(adaptiveInfo) {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM) {
                NavigationSuiteType.NavigationRail
            } else {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
            }
        }

        val items = listOf(
            AppDestinations.HOME,
            AppDestinations.FAVORITE,
            AppDestinations.PROFILE,
            AppDestinations.SHOPPING
        )

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                items.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = stringResource(it.contentDescription)
                            )
                        },
                        label = { Text(stringResource(it.label)) },
                        alwaysShowLabel = true,
                        selected = currentRoute == it.route,
                        onClick = {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            },
            layoutType = customNavSuiteType
        ) {
            AppNavGraph(navController = navController, viewModel = viewModel)
        }
    }
}

@PreviewScreenSizes
@Composable
fun AdaptiveAppPreview() {
    AppPlatant()
}

