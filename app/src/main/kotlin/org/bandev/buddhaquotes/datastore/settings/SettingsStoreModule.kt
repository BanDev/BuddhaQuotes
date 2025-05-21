package org.bandev.buddhaquotes.datastore.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bandev.buddhaquotes.settings.Settings
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SettingsStoreModule {

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext appContext: Context): DataStore<Settings> {
        return DataStoreFactory.create(
            serializer = SettingsSerializer,
            produceFile = { appContext.dataStoreFile("Settings.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()))
    }
}
