package org.bandev.buddhaquotes.architecture.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bandev.buddhaquotes.architecture.ListMapper
import org.bandev.buddhaquotes.architecture.ListWithQuotes
import org.bandev.buddhaquotes.architecture.listquotes.ListQuote
import org.bandev.buddhaquotes.architecture.listquotes.QuotesInListDao
import org.bandev.buddhaquotes.architecture.quotes.Quote
import org.bandev.buddhaquotes.items.ListData
import org.bandev.buddhaquotes.items.ListIcon

class ListRepository @Inject constructor(
    private val listDao: ListDao,
    private val listQuotesDao: QuotesInListDao
) {
    /** Get just one list */
    suspend fun get(id: Int): ListData = listDao.get(id).let {
        ListData(it.id, it.title, count(it.id), ListMapper.listIcons[it.icon])
    }

    /** Get every single list */
    fun getAll(): Flow<List<ListData>> = listDao.getAll().map { listOfQuotes ->
        listOfQuotes.map {
            ListData(it.id, it.title, count(it.id), ListMapper.listIcons[it.icon])
        }
    }

    /** Rename a list */
    suspend fun rename(id: Int, title: String): Unit = listDao.rename(id, title)

    /** Update a list's icon */
    suspend fun updateIcon(id: Int, icon: ListIcon): Unit = listDao.updateIcon(id, icon.id)

    /** New empty list */
    suspend fun new(title: String): ListData {
        listDao.create(title)
        return listDao.getLast().let {
            ListData(it.id, it.title, count(it.id), ListMapper.listIcons[it.icon])
        }
    }

    /** Delete a list */
    suspend fun delete(id: Int): Unit = listDao.delete(id)

    /** Get just one list */
    fun getFrom(id: Int): LiveData<List<Quote>> {
        return listDao.getListWithQuotes(id).map(ListWithQuotes::quotes)
    }

    /** See if a list has a quote */
    suspend fun has(quoteId: Int, listId: Int): Boolean = listQuotesDao.has(quoteId, listId) == 1

    suspend fun addTo(listId: Int, quoteId: Int) {
        listQuotesDao.add(ListQuote(listId, quoteId))
    }

    /** Remove a quote from a list */
    suspend fun removeFrom(listId: Int, quoteId: Int) {
        listQuotesDao.remove(ListQuote(listId, quoteId))
    }

    /** Count the quotes in a list */
    private suspend fun count(id: Int): Int = listQuotesDao.count(id)
}
