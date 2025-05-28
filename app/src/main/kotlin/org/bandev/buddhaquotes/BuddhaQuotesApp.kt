package org.bandev.buddhaquotes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bandev.buddhaquotes.screens.HomeScreen
import org.bandev.buddhaquotes.screens.LibrariesScreen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun BuddhaQuotesApp() {
    val navController = rememberNavController()
    val navigateTo: (Scene) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                // Saves the state so that the view models aren't reset on navigation
                // https://developer.android.com/develop/ui/compose/navigation#bottom-nav
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    NavHost(
        navController = navController,
        startDestination = Scene.Home,
    ) {
        composable<Scene.Home> {
            HomeScreen(onSettingsNavigate = navigateTo)
        }
        composable<Scene.Libraries> {
            LibrariesScreen(onBack = { navController.popBackStack() })
        }
    }
}