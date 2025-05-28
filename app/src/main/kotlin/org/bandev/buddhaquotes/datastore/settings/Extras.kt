package org.bandev.buddhaquotes.datastore.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.ui.images.Anahata
import org.bandev.buddhaquotes.ui.images.DharmaWheel
import org.bandev.buddhaquotes.ui.images.Images
import org.bandev.buddhaquotes.ui.images.MandalaVariant1
import org.bandev.buddhaquotes.ui.images.MandalaVariant3
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.BrightnessAuto
import org.bandev.buddhaquotes.ui.symbols.filled.DarkMode
import org.bandev.buddhaquotes.ui.symbols.filled.LightMode

fun Settings.Theme.isDark() = this == Settings.Theme.DARK

fun Settings.Theme.isLight() = this == Settings.Theme.LIGHT

fun Settings.Theme.isSystem() = this == Settings.Theme.SYSTEM

/**
 * Convert a [Settings.Theme] instance into a boolean equivalent to the isSystemInDarkTheme()
 * function.
 */
@Composable
fun Settings.Theme?.isDarkOrSystemInDarkTheme() = when (this) {
    Settings.Theme.LIGHT -> false
    Settings.Theme.DARK -> true
    else -> isSystemInDarkTheme()
}

/**
 * Convert a [Settings.Theme] instance into a human readable string.
 */
@Composable
fun Settings.Theme.toFormattedString() = stringResource(
    when (this) {
        Settings.Theme.LIGHT -> R.string.light
        Settings.Theme.DARK -> R.string.dark
        else -> R.string.system
    }
)

/**
 * Convert a [Settings.Theme] instance into a icon for display.
 */
fun Settings.Theme?.toFilledIcon() = when (this) {
    Settings.Theme.LIGHT -> Symbols.Filled.LightMode
    Settings.Theme.DARK -> Symbols.Filled.DarkMode
    else -> Symbols.Filled.BrightnessAuto
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Settings.Theme?.toShape() = when (this) {
    Settings.Theme.LIGHT -> MaterialShapes.Sunny.toShape()
    Settings.Theme.DARK -> MaterialShapes.Gem.toShape()
    else -> MaterialShapes.Cookie4Sided.toShape()
}

fun Settings.Image.toImage() = when (this) {
    Settings.Image.UNRECOGNIZED, Settings.Image.UNDEFINED, Settings.Image.ANAHATA -> Images.Anahata
    Settings.Image.DHARMA_WHEEL -> Images.DharmaWheel
    Settings.Image.MANDALA_1 -> Images.MandalaVariant1
    Settings.Image.MANDALA_3 -> Images.MandalaVariant3
    else -> null
}