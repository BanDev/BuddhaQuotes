package org.bandev.buddhaquotes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bandev.buddhaquotes.screens.BreathingScreen
import org.bandev.buddhaquotes.screens.DailyQuoteScreen
import org.bandev.buddhaquotes.screens.FavouritesScreen
import org.bandev.buddhaquotes.screens.LibrariesScreen
import org.bandev.buddhaquotes.screens.QuoteScreen
import org.bandev.buddhaquotes.screens.SettingsScreen
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Settings

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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLargeEmphasized
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navigateTo(Scene.Settings) },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = Symbols.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                },
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            BottomNavBar(
                navigateTo = navigateTo,
                currentDestination = navBackStackEntry?.destination
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Scene.Quotes,
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            composable<Scene.Quotes> {
                QuoteScreen()
            }
            composable<Scene.Favourites> {
                FavouritesScreen()
            }
            composable<Scene.DailyQuote> {
                DailyQuoteScreen()
            }
            composable<Scene.Meditate> {
                BreathingScreen()
            }
            composable<Scene.Settings> {
                SettingsScreen(onNavigate = navController::navigate)
            }
            composable<Scene.Libraries> {
                LibrariesScreen()
            }
        }
    }
}