package org.bandev.buddhaquotes.architecture.quotes

import org.bandev.buddhaquotes.architecture.listquotes.ListQuoteDao
import org.bandev.buddhaquotes.items.QuoteItem
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val listQuoteDao: ListQuoteDao
) {
    suspend fun get(id: Int): QuoteItem {
        val quote = quoteDao.getQuote(id)
        val isLiked = listQuoteDao.has(quote.id, 1) == 1
        return QuoteItem(quote.id,  QuoteStore.quotesWithSources.keys.toList()[id - 1], isLiked)
    }

    suspend fun getAll(): List<QuoteItem> {
        val quotes = quoteDao.getAll()
        return quotes.map { quote ->
            val isLiked = listQuoteDao.has(quote.id, 1) == 1
            QuoteItem(quote.id, QuoteStore.quotesWithSources.keys.toList()[quote.id - 1], isLiked)
        }
    }
}
