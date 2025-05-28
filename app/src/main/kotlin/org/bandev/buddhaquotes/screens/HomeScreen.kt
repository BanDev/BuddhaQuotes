package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.BottomNavBar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.Scene
import org.bandev.buddhaquotes.settings.SettingsScreen
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Settings
import org.bandev.buddhaquotes.ui.theme.RoundedDrawerSheetShape
import org.bandev.buddhaquotes.utils.ProvidesValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    onSettingsNavigate: (Scene) -> Unit,
) {
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

    val layoutDirection = LocalLayoutDirection.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LocalLayoutDirection.ProvidesValue(
        if (layoutDirection == LayoutDirection.Ltr) LayoutDirection.Rtl
        else LayoutDirection.Ltr
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerState = drawerState,
                    modifier = Modifier.fillMaxWidth(0.85f),
                    drawerShape = RoundedDrawerSheetShape,
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    windowInsets = WindowInsets(0)
                ) {
                    LocalLayoutDirection.ProvidesValue(layoutDirection) {
                        SettingsScreen(
                            onNavigate = onSettingsNavigate
                        )
                    }
                }
            }) {
            LocalLayoutDirection.ProvidesValue(layoutDirection) {
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
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
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
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Scene.Quotes,
                        modifier = Modifier.padding(paddingValues = innerPadding),
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
                    }
                }
            }
        }
    }
}
