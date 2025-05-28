package org.bandev.buddhaquotes.datastore.settings

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.bandev.buddhaquotes.settings.Settings
import java.io.IOException
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val store: DataStore<Settings>) {
    val settings: Flow<Settings> = store.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("SETTINGS", "Error reading sort order preferences: $exception")
                emit(Settings.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun setTheme(theme: Settings.Theme) {
        store.updateData { currentSettings ->
            currentSettings.toBuilder().setTheme(theme).build()
        }
    }

    suspend fun setImage(image: Settings.Image) {
        store.updateData { currentSettings ->
            currentSettings.toBuilder().setImage(image).build()
        }
    }

    suspend fun setDynamicColor(dynamicColor: Boolean) {
        store.updateData { currentSettings ->
            currentSettings.toBuilder().setDynamicColor(dynamicColor).build()
        }
    }
}
