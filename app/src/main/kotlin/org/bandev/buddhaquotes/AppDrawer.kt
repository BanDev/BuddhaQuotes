package org.bandev.buddhaquotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.items.DrawerButton

@Composable
fun AppDrawer(
    navigateTo: (route: String) -> Unit,
    currentScreen: String,
    closeDrawer: () -> Unit
) {
    val topDrawerButtons = listOf(
        DrawerButton(
            icon = Icons.Rounded.FormatQuote,
            label = "Home",
            selected = currentScreen == Scene.Home.route,
            route = scenes[0].route
        ),
        DrawerButton(
            icon = Icons.Rounded.Favorite,
            label = "Favourites",
            selected = currentScreen == Scene.Favourites.route,
            route = scenes[1].route
        ),
        DrawerButton(
            icon = Icons.Rounded.CalendarToday,
            label = "Daily Quote",
            selected = currentScreen == Scene.DailyQuote.route,
            route = scenes[2].route
        ),
        DrawerButton(
            icon = Icons.Rounded.SelfImprovement,
            label = "Meditate",
            selected = currentScreen == Scene.Meditate.route,
            route = scenes[3].route
        )
    )

    val bottomDrawerButtons = listOf(
        DrawerButton(
            icon = Icons.Rounded.Settings,
            label = "Settings",
            selected = currentScreen == Scene.Settings.route,
            route = scenes[4].route
        ),
        DrawerButton(
            icon = Icons.Rounded.Info,
            label = "About",
            selected = currentScreen == Scene.About.route,
            route = scenes[5].route
        )
    )

    ModalDrawerSheet {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(topDrawerButtons) { drawerButton ->
                NavigationDrawerItem(
                    label = { Text(drawerButton.label) },
                    selected = drawerButton.selected,
                    onClick = {
                        navigateTo(drawerButton.route)
                        closeDrawer()
                    },
                    icon = { Icon(drawerButton.icon, contentDescription = null) }
                )
            }
        }
        Spacer(Modifier.weight(1f))
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(bottomDrawerButtons) { drawerButton ->
                NavigationDrawerItem(
                    label = { Text(drawerButton.label) },
                    selected = drawerButton.selected,
                    onClick = {
                        navigateTo(drawerButton.route)
                        closeDrawer()
                    },
                    icon = { Icon(drawerButton.icon, contentDescription = null) }
                )
            }
        }
    }
}
