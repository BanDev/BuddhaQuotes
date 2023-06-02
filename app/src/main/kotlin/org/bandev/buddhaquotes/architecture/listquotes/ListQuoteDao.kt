package org.bandev.buddhaquotes.architecture.listquotes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListQuoteDao {

    /** Get every single quote from a list */
    @Query("SELECT * FROM list_quote WHERE list_id = :listId")
    suspend fun getFrom(listId: Int): List<ListQuote>

    /** Check if a quote exists in a list */
    @Query("SELECT COUNT(*) FROM list_quote WHERE list_id = :listId AND quote_id = :quoteId")
    suspend fun has(quoteId: Int, listId: Int): Int

    /** Add a quote to a list */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(listQuote: ListQuote)

    /** Remove a quote from a list */
    @Delete
    suspend fun remove(listQuote: ListQuote)

    /** Count the quotes in a list */
    @Query("SELECT COUNT(*) FROM list_quote WHERE list_id = :listId")
    suspend fun count(listId: Int): Int
}