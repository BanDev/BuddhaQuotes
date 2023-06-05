package org.bandev.buddhaquotes.architecture.quotes

import javax.inject.Inject
import org.bandev.buddhaquotes.architecture.listquotes.QuotesInListDao
import org.bandev.buddhaquotes.items.QuoteItem

class QuotesRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val quotesInListDao: QuotesInListDao
) {
    suspend fun get(id: Int): QuoteItem {
        val quote = quoteDao.getQuote(id)
        val isLiked = quotesInListDao.has(quote.id, 1) == 1
        return QuoteItem(quote.id,  QuoteStore.quotesWithSources.keys.toList()[id - 1], isLiked)
    }

    suspend fun getAll(): List<QuoteItem> {
        val quotes = quoteDao.getAll()
        return quotes.map { quote ->
            val isLiked = quotesInListDao.has(quote.id, 1) == 1
            QuoteItem(quote.id, QuoteStore.quotesWithSources.keys.toList()[quote.id - 1], isLiked)
        }
    }
}
