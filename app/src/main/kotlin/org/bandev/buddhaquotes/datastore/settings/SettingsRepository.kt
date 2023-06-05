package org.bandev.buddhaquotes.datastore.settings

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.bandev.buddhaquotes.settings.Settings

class SettingsRepository(private val store: DataStore<Settings>) {

    val settings: Flow<Settings> = store.data
        .catch { e ->
            if (e is IOException) {
                Log.e("TAG", "Error reading sort order preferences: $e")
                emit(Settings.getDefaultInstance())
            } else {
                throw e
            }
        }

    suspend fun setTheme(theme: Settings.Theme) {
        store.updateData { current ->
            current.toBuilder().setTheme(theme).build()
        }
    }
}
