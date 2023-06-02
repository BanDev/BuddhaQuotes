package org.bandev.buddhaquotes.architecture.quotes

import androidx.room.Dao
import androidx.room.Query

/**
 * Interact with the quote table of the
 * database to allow for querying quotes
 * and liking or unliking them.
 */

@Dao
interface QuoteDao {
    /** Get just one quote */
    @Query("SELECT * FROM quote WHERE id = :id")
    suspend fun getQuote(id: Int): Quote

    /** Get every single quote */
    @Query("SELECT * FROM quote")
    suspend fun getAll(): List<Quote>
}
