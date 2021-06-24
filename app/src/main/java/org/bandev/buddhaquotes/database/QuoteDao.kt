package org.bandev.buddhaquotes.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes")
    fun getAll(): List<DbQuote>

    @Query("SELECT * FROM quotes WHERE id = :id")
    fun get(id: Int): DbQuote

    @Query("UPDATE quotes SET 'like' = 1 WHERE id = :id")
    fun like(id: Int)

    @Query("UPDATE quotes SET 'like' = 0 WHERE id = :id")
    fun unlike(id: Int)

}
