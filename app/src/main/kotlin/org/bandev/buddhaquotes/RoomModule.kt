package org.bandev.buddhaquotes

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.bandev.buddhaquotes.architecture.BuddhaQuotesDatabase
import org.bandev.buddhaquotes.architecture.listquotes.QuotesInListDao
import org.bandev.buddhaquotes.architecture.lists.ListDao
import org.bandev.buddhaquotes.architecture.quotes.QuoteDao

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): BuddhaQuotesDatabase {
        return BuddhaQuotesDatabase.getDatabase(context)
    }

    @Provides
    fun providesQuoteDao(buddhaQuotesDatabase: BuddhaQuotesDatabase): QuoteDao {
        return buddhaQuotesDatabase.quote
    }

    @Provides
    fun providesListDao(buddhaQuotesDatabase: BuddhaQuotesDatabase): ListDao {
        return buddhaQuotesDatabase.list
    }

    @Provides
    fun providesQuotesInListDao(buddhaQuotesDatabase: BuddhaQuotesDatabase): QuotesInListDao {
        return buddhaQuotesDatabase.listQuote
    }
}
