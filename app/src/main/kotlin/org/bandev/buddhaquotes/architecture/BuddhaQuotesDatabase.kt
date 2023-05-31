package org.bandev.buddhaquotes.architecture

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.bandev.buddhaquotes.architecture.listquotes.ListQuote
import org.bandev.buddhaquotes.architecture.listquotes.ListQuoteDao
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotes
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotesDao
import org.bandev.buddhaquotes.architecture.quotes.Quote
import org.bandev.buddhaquotes.architecture.quotes.QuoteDao
import org.bandev.buddhaquotes.architecture.quotes.QuoteStore

@Database(
    version = 1,
    entities = [Quote::class, ListOfQuotes::class, ListQuote::class],
    exportSchema = false
)
abstract class BuddhaQuotesDatabase : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract val quote: QuoteDao

    /** Provide access to the [ListOfQuotesDao] */
    abstract val list: ListOfQuotesDao

    /** Provide access to the [ListQuoteDao] */
    abstract val listQuote: ListQuoteDao

    companion object {
        @Volatile
        private var Instance: BuddhaQuotesDatabase? = null

        fun getDatabase(context: Context): BuddhaQuotesDatabase = Instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, BuddhaQuotesDatabase::class.java, "db")
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            repeat(QuoteStore.quotesWithSources.size) {
                                db.execSQL("INSERT INTO quote DEFAULT VALUES")
                            }
                            db.execSQL("INSERT INTO list (title) VALUES ('Favourites')")
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { Instance = it }
        }
    }
}
