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
        suspend fun get(id: Int): Quote

        /** Get every single quote */
        @Query("SELECT * FROM quote")
        suspend fun getAll(): List<Quote>

        /** Like a quote */
        @Query("UPDATE quote SET `like` = 1 WHERE id = :id")
        suspend fun like(id: Int)

        /** Un-Like a quote */
        @Query("UPDATE quote SET `like` = 0 WHERE id = :id")
        suspend fun unlike(id: Int)

        /** Get all liked quotes */
        @Query("SELECT * FROM quote WHERE `like` = 1")
        suspend fun getLiked(): List<Quote>

        /** Count the quotes */
        @Query("SELECT COUNT(id) FROM quote")
        suspend fun count(): Int

        /** Count the liked quotes */
        @Query("SELECT COUNT(`like`) FROM quote WHERE `like` = 1")
        suspend fun countLiked(): Int
    }