/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.architecture

import android.content.Context
import androidx.room.*

@Database(
    version = 1,
    entities = [Db.Quote::class, Db.QuoteList::class, Db.ListQuoteLink::class]
)

/**
 * RoomDatabse class for Buddha Quotes. Stores
 * all quotes and lists used throughout the
 * application.
 * @author Jack Devey
 */

abstract class Db : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract fun quotes(): QuoteDao

    /** Provide access to the [QuoteListDao] */
    abstract fun lists(): QuoteListDao

    companion object {

        private var instance: Db? = null

        /**
         * getInstance ensure that only one
         * instance of the database is used
         */

        @Synchronized
        fun getInstance(context: Context): Db? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java, "db"
                ).createFromAsset("db.db").build()
            }
            return instance
        }

    }

    /**
     * QuoteDao allows for manipulation of
     * Quote entities such as liking
     * or retrieving quotes
     */

    @Dao
    interface QuoteDao {
        // Get just one quote
        @Query("SELECT * FROM quotes WHERE quoteId = :id")
        suspend fun get(id: Int): Quote

        // Get all quotes
        @Query("SELECT * FROM quotes")
        suspend fun getAll(): List<Quote>

        // Count quotes
        @Query("SELECT COUNT(*) FROM quotes")
        suspend fun count(): Int

        // Like a quote
        @Query("INSERT into ListQuoteLink (listId, quoteId) VALUES (0, :id)")
        suspend fun like(id: Int)

        // Dislike a quote
        @Query("DELETE FROM ListQuoteLink WHERE listId = 0 AND quoteId = :id")
        suspend fun removeLike(id: Int)

        @Query("SELECT COUNT(*) FROM ListQuoteLink WHERE listId = 0 AND quoteId = :id")
        suspend fun isLiked(id: Int): Int
    }

    /**
     * QuoteListDao allows for manipulation of
     * QuoteList entities such as adding to
     * or retrieving quotes
     */

    @Dao
    interface QuoteListDao {
        // Get all lists
        @Transaction
        @Query("SELECT * FROM lists")
        suspend fun getAll(): List<ListAndQuotes>

        // Get just one list
        @Transaction
        @Query("SELECT * FROM lists WHERE listId = :id")
        suspend fun get(id: Int): ListAndQuotes

        // Create a new list in the database
        @Insert
        suspend fun newList(record: QuoteList)

        // Update a list's icon
        @Query("UPDATE lists SET icon = :iconId WHERE listId = :listId")
        suspend fun updateIcon(listId: Int, iconId: Int)

        // Add a quote to a list
        @Query("INSERT into ListQuoteLink (listId, quoteId) VALUES (:listId, :quoteId)")
        suspend fun addQuote(listId: Int, quoteId: Int)

        // Remove a quote from a list
        @Query("DELETE FROM ListQuoteLink WHERE listId = :listId AND quoteId = :quoteId")
        suspend fun deleteQuote(listId: Int, quoteId: Int)

        // Count the items of a list
        @Query("SELECT COUNT(quoteId) FROM ListQuoteLink WHERE listId = :listId")
        suspend fun count(listId: Int): Int
    }

    /**
     * Entity for each list in the db
     */

    @Entity(tableName = "lists")
    data class QuoteList(
        @PrimaryKey(autoGenerate = true) val listId: Int, // The unique list id
        @ColumnInfo(name = "title") val title: String, // Title of the list
        @ColumnInfo(name = "system") val system: Boolean, // Can you delete the list?
        @ColumnInfo(name = "icon") val icon: Int // What icon id?
    )

    /**
     * Entity for each quote in the db
     */

    @Entity(tableName = "quotes")
    data class Quote(
        @PrimaryKey val quoteId: Int, // The unique quote id
        @ColumnInfo(name = "like", defaultValue = "0") var like: Boolean, // Has it been liked?
        // Each quote doesn't actually hold the quote!
        // It is pulled from strings resources each time
        // for translations.
    )

    /**
     * Entity for a linking table in order
     * to maintain a many -> many relationship
     */

    @Entity(primaryKeys = ["listId", "quoteId"])
    data class ListQuoteLink(
        val id: Int?,
        val listId: Int,
        val quoteId: Int
    )

    /**
     * Entity for getting lists with their inner
     * quotes.
     */

    data class ListAndQuotes(
        val id: Int?,
        @Embedded val list: QuoteList,
        @Relation(
            parentColumn = "listId",
            entityColumn = "quoteId",
            associateBy = Junction(ListQuoteLink::class)
        )
        val quotes: List<Quote>
    )

}