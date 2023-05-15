package org.bandev.buddhaquotes.settings

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

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

