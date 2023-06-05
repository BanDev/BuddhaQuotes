package org.bandev.buddhaquotes.datastore.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.settings.Settings

class SettingsViewModel(context: Context) : ViewModel() {

    private val repository = SettingsRepository(context.SettingsStoreCreator)

    val settings = repository.settings.asLiveData()

    fun setTheme(theme: Settings.Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }
}
