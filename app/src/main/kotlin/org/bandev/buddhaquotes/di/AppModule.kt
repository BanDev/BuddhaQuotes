package org.bandev.buddhaquotes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bandev.buddhaquotes.datastore.settings.SettingsSerializer
import org.bandev.buddhaquotes.db.BuddhaQuotesDatabase
import org.bandev.buddhaquotes.db.QuoteDao
import org.bandev.buddhaquotes.settings.Settings
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BuddhaQuotesDatabase {
        /** The asset database user version pragma must match the database schema version.
         * Otherwise, destructive migration will run every time.
         * https://stackoverflow.com/a/71283218
         */

        return Room.databaseBuilder(context, BuddhaQuotesDatabase::class.java, "quotes-db")
            .createFromAsset("quotes.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideQuoteDao(db: BuddhaQuotesDatabase): QuoteDao = db.quoteDao()

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext appContext: Context): DataStore<Settings> {
        return DataStoreFactory.create(
            serializer = SettingsSerializer,
            produceFile = { appContext.dataStoreFile("settings.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
