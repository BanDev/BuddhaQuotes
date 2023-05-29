package org.bandev.buddhaquotes.architecture

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotes
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotesDao
import org.bandev.buddhaquotes.architecture.quotes.Quote
import org.bandev.buddhaquotes.architecture.quotes.QuoteDao
import org.bandev.buddhaquotes.architecture.quotes.QuoteStore

@Database(
    version = 1,
    entities = [Quote::class, ListOfQuotes::class, BuddhaQuotesDatabase.ListQuote::class],
    exportSchema = false
)
abstract class BuddhaQuotesDatabase : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract fun quote(): QuoteDao

    /** Provide access to the [ListOfQuotesDao] */
    abstract fun list(): ListOfQuotesDao

    /** Provide access to the [ListQuoteDao] */
    abstract fun listQuote(): ListQuoteDao

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

    /**
     * Interact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    @Dao
    interface ListQuoteDao {

        /** Get every single quote from a list */
        @Query("SELECT * FROM list_quote WHERE list_id = :listId")
        suspend fun getFrom(listId: Int): MutableList<ListQuote>

        /** Count the quotes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `list_id` = :listId AND `id` = :quoteId")
        suspend fun has(quoteId: Int, listId: Int): Int

        /** Add a quote to a list */
        @Insert
        suspend fun add(listQuote: ListQuote)

        /** Remove a quote from a list */
        @Delete
        suspend fun removeFrom(listQuote: ListQuote)

        /** Count the quotes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `list_id` = :listId")
        suspend fun count(listId: Int): Int

        @Transaction
        suspend fun addQuoteToList(listId: Int, quoteId: Int) {
            add(ListQuote(listId, quoteId))
        }
    }

    @Entity(
        tableName = "list_quote",
        primaryKeys = ["list_id", "id"]
    )
    data class ListQuote(
        @ColumnInfo(name = "list_id") val listId: Int,
        val id: Int
    )
}
