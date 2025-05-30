package org.bandev.buddhaquotes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieConstants
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.screens.DailyQuoteScreen
import org.bandev.buddhaquotes.screens.FavouritesScreen
import org.bandev.buddhaquotes.screens.HomeScreen
import org.bandev.buddhaquotes.screens.MeditateScreen
import org.bandev.buddhaquotes.screens.SettingsScreen
import org.bandev.buddhaquotes.screens.about.AboutScene
import org.bandev.buddhaquotes.sheets.HelpSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BuddhaQuotesApp() {
    var toolbarTitle by remember { mutableStateOf("") }
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Scene.Home.route
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                navigateTo = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            // Saves the state so that the view models aren't reset on navigation
                            // https://developer.android.com/develop/ui/compose/navigation#bottom-nav
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                currentScreen = currentRoute,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(toolbarTitle) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        AnimatedVisibility(visible = navController.currentDestination?.route == Scene.Home.route) {
                            IconButton(onClick = { openBottomSheet = !openBottomSheet }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.HelpOutline,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                )
            },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
        ) { paddingValues ->
            val pagerState = rememberPagerState(pageCount = { 3 })
            NavHost(
                navController = navController,
                startDestination = Scene.Home.route,
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                composable(Scene.Home.route) {
                    toolbarTitle = stringResource(R.string.app_name)
                    HomeScreen(
                        pagerState = pagerState,
                    )
                }
                composable(Scene.Favourites.route) { backStackEntry ->
                    toolbarTitle = stringResource(id = R.string.favourites)
                    FavouritesScreen()
                }
                composable(Scene.DailyQuote.route) {
                    toolbarTitle = stringResource(R.string.daily_quote)
                    DailyQuoteScreen()
                }
                composable(Scene.Meditate.route) {
                    toolbarTitle = stringResource(R.string.meditate)
                    MeditateScreen()
                }
                composable(Scene.Settings.route) {
                    toolbarTitle = stringResource(R.string.settings)
                    SettingsScreen()
                }
                composable(Scene.About.route) {
                    toolbarTitle = stringResource(R.string.about)
                    AboutScene()
                }
            }
            if (openBottomSheet && navController.currentDestination?.route == Scene.Home.route) {
                when (pagerState.currentPage) {
                    0 -> HelpSheet(
                        sheetState = bottomSheetState,
                        onClose = { openBottomSheet = false },
                        animationResId = R.raw.flower,
                        helpTitle = "Quote help",
                        helpText = """
                            You can press the next button or swipe down from the top to get a new quote.
                            
                            You can also change the image at the bottom by holding down on it which will bring up a selection of 16 image options.
                        """.trimIndent()
                    )

                    1 -> HelpSheet(
                        sheetState = bottomSheetState,
                        onClose = { openBottomSheet = false },
                        animationResId = R.raw.lists,
                        helpTitle = "List help",
                        helpText = """
                            These are your lists. You can access your favourite quotes from here as well as create new lists to categorise quotes into groups.
                            
                            New lists can be created by pressing the add (➕) button at the bottom of the screen and typing in a name.
                        """.trimIndent()
                    )

                    2 -> HelpSheet(
                        sheetState = bottomSheetState,
                        onClose = { openBottomSheet = false },
                        animationResId = R.raw.meditation,
                        helpTitle = "Meditate help",
                        helpText = "You can start a meditation session by pressing the start button below and inputting the time you want to meditate for.",
                        animationIterations = LottieConstants.IterateForever
                    )
                }
            }
        }
    }
}