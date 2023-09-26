package org.bandev.buddhaquotes

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.toBoolean
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel = SettingsViewModel(LocalContext.current)
            val themeState by settingsViewModel.settings.observeAsState(Settings.getDefaultInstance())
            val darkTheme = themeState.theme.toBoolean()

            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                )
                onDispose {}
            }

            BuddhaQuotesTheme {
                BuddhaQuotesApp()
            }
        }
    }
}