package org.bandev.buddhaquotes.datastore.imagePref

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
import org.bandev.buddhaquotes.imagepref.ImagePref
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImagePrefStoreModule {

    @Singleton
    @Provides
    fun provideImagePrefDataStore(@ApplicationContext appContext: Context): DataStore<ImagePref> {
        return DataStoreFactory.create(
            serializer = ImagePrefSerializer,
            produceFile = { appContext.dataStoreFile("ImagePref.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()))
    }
}
