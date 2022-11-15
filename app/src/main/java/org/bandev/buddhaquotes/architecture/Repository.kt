/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.architecture

import android.app.Application
import org.bandev.buddhaquotes.items.List
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.Quote

/**
 * A level of abstraction between the ui
 * and the db layers. Converts db classes
 * to ui classes.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

class Repository(val application: Application) {

    private val db = Db.getInstance(application.applicationContext)!!
    private val qm = QuoteMapper
    private val lm = ListMapper(application)

    /**
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    inner class Quotes {

        private var dao = db.quote()

        /** Get just one quote */
        suspend fun get(id: Int): Quote = qm.convert(dao.get(id))

        /** Get every single quote */
        suspend fun getAll(): MutableList<Quote> = qm.convertAll(dao.getAll())

        /** Like a quote */
        suspend fun like(id: Int): Unit = dao.like(id)

        /** Un-Like a quote */
        suspend fun unlike(id: Int): Unit = dao.unlike(id)

        /** Get all liked quotes */
        suspend fun getLiked(): MutableList<Quote> = qm.convertAll(dao.getLiked())

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

        private var dao = db.list()

        /** Get just one list */
        suspend fun get(id: Int): List = lm.convert(dao.get(id), this@Repository.ListQuotes())

        /** Get every single list */
        suspend fun getAll(): MutableList<List> = lm.convertAll(dao.getAll(), this@Repository.ListQuotes())

        /** Rename a list */
        suspend fun rename(id: Int, title: String): Unit = dao.rename(id, title)

        /** Update a list's icon */
        suspend fun updateIcon(id: Int, icon: ListIcon): Unit = dao.updateIcon(id, icon.id)

        /** New empty list */
        suspend fun new(title: String): List {
            dao.create(title)
            return lm.convert(dao.getLast(), this@Repository.ListQuotes())
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

        private var dao = db.listQuote()

        /** Get just one list */
        suspend fun getFrom(id: Int): MutableList<Quote> {
            if (id == 0) return Quotes().getLiked()
            val quotes = mutableListOf<Quote>()
            for (lq in dao.getFrom(id)) quotes.add(qm.convert(db.quote().get(lq.quoteId)))
            return quotes
        }

        /** See if a list has a quote */
        suspend fun has(quoteId: Int, listId: Int): Boolean {
            return dao.has(quoteId, listId) == 1
        }

        /** Add a quote to a list */
        suspend fun addTo(id: Int, quote: Quote) {
            if (id == 0) return Quotes().like(quote.id)
            dao.addTo(id, quote.id, count(id).toDouble())
        }

        /** Add a quote to a list from just quote id */
        suspend fun addTo(listId: Int, quoteId: Int) {
            if (listId == 0) return Quotes().like(quoteId)
            dao.addTo(listId, quoteId, count(listId).toDouble())
        }

        /** Remove a quote from a list */
        suspend fun removeFrom(id: Int, quote: Quote) {
            if (id == 0) return db.quote().unlike(quote.id)
            dao.removeFrom(id, quote.id)
        }

        /** Count the quotes in a list */
        suspend fun count(id: Int): Int {
            if (id == 0) return db.quote().countLiked()
            return dao.count(id)
        }

    }
}