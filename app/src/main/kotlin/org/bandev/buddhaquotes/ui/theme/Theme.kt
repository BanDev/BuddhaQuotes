package org.bandev.buddhaquotes.ui.theme

import android.graphics.Color
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

internal const val SpringDefaultDampingRatio = Spring.DampingRatioMediumBouncy
internal const val SpringDefaultStiffness = Spring.StiffnessLow

val supportsDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BuddhaQuotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && supportsDynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && supportsDynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> darkColorScheme()
        else -> expressiveLightColorScheme()
    }

    UpdateEdgeToEdge(darkTheme)

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = BuddhaQuotesTypography,
        content = content
    )
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

