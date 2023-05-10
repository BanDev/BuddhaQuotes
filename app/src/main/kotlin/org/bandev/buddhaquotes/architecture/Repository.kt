package org.bandev.buddhaquotes.architecture

import android.app.Application
import org.bandev.buddhaquotes.architecture.quotes.Quote
import org.bandev.buddhaquotes.items.ListData
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.QuoteItem

/**
 * A level of abstraction between the ui
 * and the db layers. Converts db classes
 * to ui classes.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

class Repository(application: Application) {

    private val database = BuddhaQuotesDatabase.getDatabase(application)

    /**
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    inner class Quotes {

        private var dao = database.quote()

        /** Get just one quote */
        suspend fun get(id: Int): QuoteItem = dao.get(id).toUIQuote()

        /** Get every single quote */
        suspend fun getAll(): List<QuoteItem> = dao.getAll().map(Quote::toUIQuote)

        /** Like a quote */
        suspend fun like(id: Int): Unit = dao.like(id)

        /** Un-Like a quote */
        suspend fun unlike(id: Int): Unit = dao.unlike(id)

        /** Get all liked quotes */
        suspend fun getLiked(): List<QuoteItem> = dao.getLiked().map(Quote::toUIQuote)

        /** Count the quotes */
        suspend fun count(): Int = dao.count()

        /** Count the liked quotes */
        suspend fun countLiked(): Int = dao.countLiked()
    }

    /**
     * Interact with the list table of the
     * database to allow for adding or removing
     * list records and renaming and updating
     * some UI elements like title and icon.
     */

    inner class Lists {

        private var dao = database.list()

        /** Get just one list */
        suspend fun get(id: Int): ListData = ListMapper.convert(dao.get(id), this@Repository.ListQuotes())

        /** Get every single list */
        suspend fun getAll(): List<ListData> = dao.getAll().map { ListMapper.convert(it, this@Repository.ListQuotes()) }

        /** Rename a list */
        suspend fun rename(id: Int, title: String): Unit = dao.rename(id, title)

        /** Update a list's icon */
        suspend fun updateIcon(id: Int, icon: ListIcon): Unit = dao.updateIcon(id, icon.id)

        /** New empty list */
        suspend fun new(title: String): ListData {
            dao.create(title)
            return ListMapper.convert(dao.getLast(), this@Repository.ListQuotes())
        }

        /** Delete a list */
        suspend fun delete(id: Int): Unit = dao.delete(id)

    }

    /**
     * Interact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    inner class ListQuotes {

        private var dao = database.listQuote()

        /** Get just one list */
        suspend fun getFrom(id: Int): List<QuoteItem> {
            return if (id == 1) {
                Quotes().getLiked()
            } else {
                dao.getFrom(id).map { listQuote ->
                    database.quote().get(listQuote.id).toUIQuote()
                }
            }
        }

        /** See if a list has a quote */
        suspend fun has(quoteId: Int, listId: Int): Boolean = dao.has(quoteId, listId) == 1

        /** Add a quote to a list */
        suspend fun addTo(listId: Int, quote: QuoteItem) {
            if (listId == 1) return Quotes().like(quote.id)
            dao.add(BuddhaQuotesDatabase.ListQuote(listId, quote.id))
        }

        /** Remove a quote from a list */
        suspend fun removeFrom(listId: Int, quote: QuoteItem) {
            if (listId == 1) return database.quote().unlike(quote.id)
            dao.removeFrom(BuddhaQuotesDatabase.ListQuote(listId, quote.id))
        }

        /** Count the quotes in a list */
        suspend fun count(id: Int): Int {
            return if (id == 1) database.quote().countLiked() else dao.count(id)
        }
    }
}
