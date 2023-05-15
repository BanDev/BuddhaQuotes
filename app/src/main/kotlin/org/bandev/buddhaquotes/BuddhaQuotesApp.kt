package org.bandev.buddhaquotes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbnb.lottie.compose.LottieConstants
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotes.scenes.AboutScene
import org.bandev.buddhaquotes.scenes.DailyQuoteScene
import org.bandev.buddhaquotes.scenes.HomeScene
import org.bandev.buddhaquotes.scenes.InsideListScene
import org.bandev.buddhaquotes.scenes.ListsScene
import org.bandev.buddhaquotes.scenes.MeditateScene
import org.bandev.buddhaquotes.scenes.SettingsScene
import org.bandev.buddhaquotes.settings.SettingsViewModel
import org.bandev.buddhaquotes.settings.toBoolean
import org.bandev.buddhaquotes.sheets.HelpSheet
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme
import org.bandev.buddhaquotes.ui.theme.EdgeToEdgeContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BuddhaQuotesApp(viewModel: BuddhaQuotesViewModel = viewModel()) {
    val settings = SettingsViewModel(LocalContext.current)
    val darkTheme = settings.getThemeLive().toBoolean()

    BuddhaQuotesTheme(darkTheme = darkTheme) {
        EdgeToEdgeContent(darkTheme = darkTheme) {
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

            Column {
                ModalNavigationDrawer(
                    drawerContent = {
                        AppDrawer(
                            navigateTo = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
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
                                        IconButton(
                                            onClick = { openBottomSheet = !openBottomSheet }
                                        ) {
                                            Icon(Icons.Rounded.HelpOutline, contentDescription = null)
                                        }
                                    }
                                },
                                windowInsets = WindowInsets.statusBars
                                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                            )
                        }
                    ) { paddingValues ->
                        val pagerState = rememberPagerState()
                        NavHost(
                            navController = navController,
                            startDestination = Scene.Home.route,
                            modifier = Modifier.padding(paddingValues = paddingValues)
                        ) {
                            composable(Scene.Home.route) {
                                toolbarTitle = stringResource(R.string.app_name)
                                HomeScene(navController = navController, pagerState = pagerState)
                            }
                            composable(Scene.Lists.route) {
                                toolbarTitle = stringResource(R.string.your_lists)
                                ListsScene(navController = navController)
                            }
                            composable(
                                route = "${Scene.InsideList.route}/{listId}",
                                arguments = listOf(navArgument("listId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val listId = backStackEntry.arguments?.getInt("listId") ?: 0
                                val favourites = stringResource(id = R.string.favourites)
                                LaunchedEffect(Unit) {
                                    toolbarTitle = if (listId == 0) {
                                        favourites
                                    } else {
                                        viewModel.Lists().get(listId).title
                                    }
                                }
                                InsideListScene(listId)
                            }
                            composable(Scene.DailyQuote.route) {
                                toolbarTitle = stringResource(R.string.daily_quote)
                                DailyQuoteScene()
                            }
                            composable(Scene.Meditate.route) {
                                toolbarTitle = stringResource(R.string.meditate)
                                MeditateScene()
                            }
                            composable(Scene.Settings.route) {
                                toolbarTitle = stringResource(R.string.settings)
                                SettingsScene()
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
        }
    }
}