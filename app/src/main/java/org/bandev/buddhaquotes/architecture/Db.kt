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
    entities = [Db.Quote::class, Db.QuoteList::class]
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
                )
                    .createFromAsset("db.db")
                    .build()
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
        @Query("SELECT * FROM quotes WHERE id = :id")
        suspend fun get(id: Int): Quote

        // Get all quotes
        @Query("SELECT * FROM quotes")
        suspend fun getAll(): List<Quote>

        // Count quotes
        @Query("SELECT COUNT(*) FROM quotes")
        suspend fun count(): Int

        // Define if a quote is liked or not
        @Query("UPDATE quotes SET 'like' = :like WHERE id = :id")
        suspend fun setLike(id: Int, like: Boolean)
    }

    /**
     * QuoteListDao allows for manipulation of
     * QuoteList entities such as adding to
     * or retrieving quotes
     */

    @Dao
    interface QuoteListDao {
        // Get all lists
        @Query("SELECT * FROM lists")
        suspend fun getAll(): List<QuoteList>

        // Get just one list
        @Query("SELECT * FROM lists WHERE id = :id")
        suspend fun get(id: Int): QuoteList

        // Delete a list
        @Delete
        suspend fun delete(list: QuoteList)
    }

    /**
     * Entity for each list in the db
     */

    @Entity(tableName = "lists")
    data class QuoteList(
        @PrimaryKey val id: Int, // The unique list id
        @ColumnInfo(name = "title") val title: String, // Title of the list
        @ColumnInfo(name = "system") val system: Int // Can you delete the list?
    )

    /**
     * Entity for each quote in the db
     */

    @Entity(tableName = "quotes")
    data class Quote(
        @PrimaryKey val id: Int, // The unique quote id
        @ColumnInfo(name = "like", defaultValue = "0") val like: Boolean, // Has it been liked?
        // Each quote doesn't actually hold the quote!
        // It is pulled from strings resources each time
        // for translations.
    )

}