package org.bandev.buddhaquotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

@Dao
interface QuoteListDao {

    @Query("SELECT * FROM lists")
    fun getAll(): List<QuoteList>

    @Query("SELECT * FROM lists WHERE id = :id")
    fun get(id: Int): QuoteList

    @Delete
    fun delete(list: QuoteList)
}
