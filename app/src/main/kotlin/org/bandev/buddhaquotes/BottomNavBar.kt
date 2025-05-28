package org.bandev.buddhaquotes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.BookmarkHeart
import org.bandev.buddhaquotes.ui.symbols.filled.CalendarToday
import org.bandev.buddhaquotes.ui.symbols.filled.FormatQuote
import org.bandev.buddhaquotes.ui.symbols.filled.SelfImprovement
import org.bandev.buddhaquotes.ui.symbols.outlined.BookmarkHeart
import org.bandev.buddhaquotes.ui.symbols.outlined.CalendarToday
import org.bandev.buddhaquotes.ui.symbols.outlined.FormatQuote

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    navigateTo: (route: Scene) -> Unit,
    currentDestination: NavDestination?
) {
    val topLevelRoutes = listOf(
        TopLevelRoute(
            name = "Quotes",
            route = Scene.Quotes,
            selectedIcon = Symbols.Filled.FormatQuote,
            unselectedIcon = Symbols.Outlined.FormatQuote
        ),
        TopLevelRoute(
            name = stringResource(R.string.daily_quote),
            route = Scene.DailyQuote,
            selectedIcon = Symbols.Filled.CalendarToday,
            unselectedIcon = Symbols.Outlined.CalendarToday
        ),
        TopLevelRoute(
            name = stringResource(R.string.favourites),
            route = Scene.Favourites,
            selectedIcon = Symbols.Filled.BookmarkHeart,
            unselectedIcon = Symbols.Outlined.BookmarkHeart
        ),
        TopLevelRoute(
            name = "Meditate",
            route = Scene.Meditate,
            selectedIcon = Symbols.Filled.SelfImprovement,
            unselectedIcon = Symbols.Filled.SelfImprovement
        ),
    )

    FlexibleBottomAppBar {
        topLevelRoutes.forEach { topLevelRoute ->
            val selected =
                currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) topLevelRoute.selectedIcon else topLevelRoute.unselectedIcon,
                        contentDescription = topLevelRoute.name
                    )
                },
                label = { Text(topLevelRoute.name) },
                selected = selected,
                onClick = { navigateTo(topLevelRoute.route) }
            )
        }
    }
}

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
