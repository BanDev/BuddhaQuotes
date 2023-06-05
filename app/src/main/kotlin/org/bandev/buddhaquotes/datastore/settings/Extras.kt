package org.bandev.buddhaquotes.datastore.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness6
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.settings.Settings

/**
 * Convert a [Settings.Theme] instance
 * into a boolean equivalent to the
 * isSystemInDarkTheme() function.
 */

@Composable
fun Settings.Theme?.toBoolean() = when(this) {
    Settings.Theme.LIGHT -> false
    Settings.Theme.DARK -> true
    else -> isSystemInDarkTheme()
}

/**
 * Convert a [Settings.Theme] instance
 * into a human readable string for
 * outputting to the user.
 */

@Composable
fun Settings.Theme.toFormattedString() = stringResource(when(this) {
    Settings.Theme.LIGHT -> R.string.light
    Settings.Theme.DARK -> R.string.dark
    else -> R.string.system
})

/**
 * Convert a [Settings.Theme] instance
 * into a icon for display.
 */

@Composable
fun Settings.Theme?.toIcon() = when(this) {
    Settings.Theme.LIGHT -> Icons.Rounded.LightMode
    Settings.Theme.DARK -> Icons.Rounded.DarkMode
    else -> Icons.Rounded.Brightness6
}