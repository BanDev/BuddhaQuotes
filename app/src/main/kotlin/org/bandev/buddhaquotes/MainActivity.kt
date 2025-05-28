package org.bandev.buddhaquotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.isDarkOrSystemInDarkTheme
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val settings by settingsViewModel.settings.observeAsState(Settings.getDefaultInstance())
            val darkTheme = settings.theme.isDarkOrSystemInDarkTheme()
            val dynamicColor by settingsViewModel.dynamicColour.observeAsState(true)

            BuddhaQuotesTheme(darkTheme, dynamicColor) {
                Surface {
                    BuddhaQuotesApp()
                }
            }
        }
    }
}
