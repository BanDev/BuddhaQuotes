package org.bandev.buddhaquotes.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(context: Context) : ViewModel() {

    private val repository = SettingsRepository(context.SettingsStoreCreator)

    val settings = repository.settings.asLiveData()

    fun setTheme(theme: Settings.Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }

    @Composable
    fun getThemeLive() = settings.observeAsState().value?.theme

}