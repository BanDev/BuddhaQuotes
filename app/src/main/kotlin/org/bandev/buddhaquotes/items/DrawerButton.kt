package org.bandev.buddhaquotes.items

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerButton(
    val icon: ImageVector,
    val label: String = "",
    val selected: Boolean,
    val route: String,
    val modifier: Modifier = Modifier
)