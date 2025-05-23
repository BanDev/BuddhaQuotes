package org.bandev.buddhaquotes.repository

import kotlinx.coroutines.flow.Flow
import org.bandev.buddhaquotes.db.QuoteDao
import org.bandev.buddhaquotes.model.Quote
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val dao: QuoteDao
) {
    fun getAllQuotes(): Flow<List<Quote>> = dao.getAllQuotes()
    fun getFavouriteQuotes(): Flow<List<Quote>> = dao.getFavouriteQuotes()
    suspend fun updateLiked(id: Int, isLiked: Boolean) = dao.updateLiked(id, isLiked)
}
