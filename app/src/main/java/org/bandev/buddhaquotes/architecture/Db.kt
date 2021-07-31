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
import org.bandev.buddhaquotes.items.Quote

@Database(
    version = 1,
    entities = [Db.Quote::class, Db.List1::class, Db.ListQuote::class]
)

/**
 * Room databse class for Buddha Quotes. Stores
 * all quotes and lists used throughout the
 * application.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

abstract class Db : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract fun quote(): QuoteDao

    /** Provide access to the [ListDao] */
    abstract fun list(): ListDao

    /** Provide access to the [ListQuoteDao] */
    abstract fun listQuote(): ListQuoteDao

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
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    @Dao
    interface QuoteDao {

        /** Get just one quote */
        @Query("SELECT * FROM quote WHERE id = :id")
        suspend fun get(id: Int): Quote

        /** Get every single quote */
        @Query("SELECT * FROM quote")
        suspend fun getAll(): MutableList<Quote>

        /** Like a quote */
        @Query("UPDATE quote SET `like` = 1 WHERE id = :id")
        suspend fun like(id: Int)

        /** Un-Like a quote */
        @Query("UPDATE quote SET `like` = 0 WHERE id = :id")
        suspend fun unlike(id: Int)

        /** Get all liked quotes */
        @Query("SELECT * FROM quote WHERE `like` = 1")
        suspend fun getLiked(): MutableList<Quote>

        /** Count the quotes */
        @Query("SELECT COUNT(id) FROM quote")
        suspend fun count(): Int

        /** Count the liked quotes */
        @Query("SELECT COUNT(`like`) FROM quote WHERE `like` = 1")
        suspend fun countLiked(): Int

    }

    /**
     * Interact with the list table of the
     * database to allow for adding or removing
     * list records and renaming and updating
     * some UI elements like title and icon.
     */

    @Dao
    interface ListDao {

        /** Get just one list */
        @Query("SELECT * FROM list WHERE id = :id")
        suspend fun get(id: Int): List1

        /** Get every single list */
        @Query("SELECT * FROM list")
        suspend fun getAll(): MutableList<List1>

        /** Get the latest list in the db */
        @Query("SELECT * FROM list ORDER BY ID DESC LIMIT 1")
        suspend fun getLast(): List1

        /** Rename a list */
        @Query("UPDATE list SET `title` = :name WHERE id = :id")
        suspend fun rename(id: Int, name: String)

        /** Update a list's icon */
        @Query("UPDATE list SET `icon` = :icon WHERE id = :id")
        suspend fun updateIcon(id: Int, icon: Int)

        /** New empty list */
        @Query("INSERT INTO list (`title`) VALUES (:title)")
        suspend fun create(title: String)

        /** Delete a list */
        @Query("DELETE FROM list WHERE id = :id")
        suspend fun delete(id: Int)

        /** Count lists */
        @Query("SELECT COUNT(id) FROM list")
        suspend fun count(): Int

    }

    /**
     * Intereact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    @Dao
    interface ListQuoteDao {

        /** Get every single quote from a list */
        @Query("SELECT * FROM list_quote WHERE list_id = :listId ORDER BY `order` ASC")
        suspend fun getFrom(listId: Int): MutableList<ListQuote>

        /** Add a quote to a list */
        @Query("INSERT INTO list_quote (`list_id`, `quote_id`, `order`) VALUES (:listId, :quoteId, :order)")
        suspend fun addTo(listId: Int, quoteId: Int, order: Double)

        /** Remove a quote from a list */
        @Query("DELETE FROM list_quote WHERE `list_id` = :listId AND `quote_id` = :quoteId")
        suspend fun removeFrom(listId: Int, quoteId: Int)

        /** Count the qoutes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `list_id` = :listId")
        suspend fun count(listId: Int): Int

    }

    /**
     * A quote in the application.
     *
     * Each quote has a unique id, which is
     * used to get the translated quote in the
     * repository layer.
     *
     * Each quote also has a liked boolean, it
     * makes more sense to attempt it this way
     * rather than looking it up with a WHERE
     * search everytime we need to display a
     * red heart.
     */

    @Entity(tableName = "quote")
    data class Quote(
        @PrimaryKey val id: Int, // The unique quote id
        @ColumnInfo(name = "like", defaultValue = "0") var like: Boolean // Is it liked?
        // Each quote doesn't hold its quote!
        // This is pulled from elsewhere in the
        // app to allow for translations to occur.
    )

    /**
     * A list in the application.
     *
     * Each list has its own id, so we dont need
     * to worry about clashing names like
     * previously. They also have their own user
     * changable icon, so the id of that is also
     * stored in the db.
     *
     * Each list also has a boolean to describe
     * if the list is 'system' or not. At the
     * moment, this is used for deciding if a list
     * can be deleted e.g. Favourites cannot be but
     * a custom user generated one could be.
     */

    @Entity(tableName = "list")
    data class List1(
        @PrimaryKey val id: Int, // The unique list id
        @ColumnInfo(typeAffinity = 2) val title: String, // The title of the list
        @ColumnInfo(defaultValue = "0") val system: Boolean, // Is it a 'special' list?
        @ColumnInfo(defaultValue = "0") val icon: Int // The chosen icon of the list
    )

    /**
     * So that we can place quotes in the order,
     * that they were added, a custom Many <-> Many
     * solution is required.
     *
     * ListQuote should never be exposed to UI levels
     * and links together quotes and their lists with
     * also an ordering number.
     *
     * When defining an order, remember that bigger
     * numbers are shown last and you can use decimal
     * values.
     */

    @Entity(tableName = "list_quote")
    data class ListQuote(
        @PrimaryKey val id: Int, // The unique connection id
        @ColumnInfo(name = "list_id") val listId: Int, // The id of the list
        @ColumnInfo(name = "quote_id") val quoteId: Int, // The id of the quote
        @ColumnInfo val order: Double, // The order (ASC)
    )

}