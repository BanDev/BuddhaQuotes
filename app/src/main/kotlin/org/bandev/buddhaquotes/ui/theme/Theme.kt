package org.bandev.buddhaquotes.ui.theme

import android.graphics.Color
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.isDark
import org.bandev.buddhaquotes.settings.Settings

internal const val SpringDefaultDampingRatio = Spring.DampingRatioMediumBouncy
internal const val SpringDefaultStiffness = Spring.StiffnessLow

@Composable
fun BuddhaQuotesTheme(content: @Composable () -> Unit) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val themeState by settingsViewModel.settings.observeAsState(Settings.getDefaultInstance())
    val isDarkTheme = themeState.theme.isDark()

    val isDynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme = when {
        isDynamicColorSupported && isDarkTheme -> dynamicDarkColorScheme(LocalContext.current)
        isDynamicColorSupported && !isDarkTheme -> dynamicLightColorScheme(LocalContext.current)
        isDarkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    UpdateEdgeToEdge(isDarkTheme)

    MaterialTheme(colorScheme = colorScheme, content = content)
}


@Composable
fun UpdateEdgeToEdge(darkVariant: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return

    SideEffect {
        val barStyle = if (darkVariant) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
        (view.context as ComponentActivity).enableEdgeToEdge(barStyle, barStyle)
    }
}

