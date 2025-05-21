package org.bandev.buddhaquotes.datastore.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.settings.Settings
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: SettingsRepository) : ViewModel() {
    val settings = repository.settings.asLiveData()

    fun setTheme(theme: Settings.Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }
}
