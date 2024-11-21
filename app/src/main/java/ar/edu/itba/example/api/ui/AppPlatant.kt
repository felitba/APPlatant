package ar.edu.itba.example.api.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.itba.example.api.ui.home.FavoriteScreen
import ar.edu.itba.example.api.ui.home.HomeScreen
import ar.edu.itba.example.api.ui.home.ProfileScreen
import ar.edu.itba.example.api.ui.home.ShoppingScreen
import ar.edu.itba.example.api.ui.navigation.AppDestinations
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme

@Composable
fun AdaptiveApp() {
    APIMutableStateTheme {
        val adaptiveInfo = currentWindowAdaptiveInfo()
        val customNavSuiteType = with(adaptiveInfo) {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM) {
                NavigationSuiteType.NavigationRail
            } else {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
            }
        }
        var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = stringResource(it.contentDescription)
                            )
                        },
                        label = { Text(stringResource(it.label)) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            },
            layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        ) {
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen()
                AppDestinations.FAVORITE -> FavoriteScreen()
                AppDestinations.SHOPPING -> ShoppingScreen()
                AppDestinations.PROFILE -> ProfileScreen()
            }
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240")
@Composable
fun AdaptiveAppPreview() {
    AdaptiveApp()
}

