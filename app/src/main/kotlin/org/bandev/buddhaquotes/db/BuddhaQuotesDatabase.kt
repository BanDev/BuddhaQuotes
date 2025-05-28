package org.bandev.buddhaquotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.bandev.buddhaquotes.model.Quote

@Database(
    version = 1,
    entities = [Quote::class],
)
abstract class BuddhaQuotesDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
