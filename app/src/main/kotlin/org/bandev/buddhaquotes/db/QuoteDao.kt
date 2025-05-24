package org.bandev.buddhaquotes.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.bandev.buddhaquotes.model.Quote

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE is_liked = 1")
    fun getFavouriteQuotes(): Flow<List<Quote>>

    @Query("UPDATE quotes SET is_liked = :isLiked WHERE id = :id")
    suspend fun updateLiked(id: Int, isLiked: Boolean)
}
