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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.items.QuoteList
import org.bandev.buddhaquotes.items.QuoteListWithQuotes

/**
 * ListRepository acts as a level of
 * abstraction when making db requests
 * for list objects.
 */

class ListRepository(val application: Application) {
    private val database = Db.getInstance(application.applicationContext)!!
    private val quoteRepository = QuoteRepository(application)
    private val quoteFinder = QuoteFinder(quoteRepository)
    private val listIconManager = ListIconManager(application)

    /** Get a List & Quotes using its key */
    suspend fun get(id: Int): QuoteListWithQuotes {
        val list = database.lists().get(id)
        return QuoteListWithQuotes(
            list.list.listId, list.list.title,
            list.list.system, quoteFinder.convertList(list.quotes)
        )
    }

    /** Get all Lists and their quotes */
    suspend fun getAll(): List<QuoteListWithQuotes> {
        val newLists = mutableListOf<QuoteListWithQuotes>()
        for ((_, list1, quotes) in database.lists().getAll()) {
            newLists.add(
                QuoteListWithQuotes(
                    list1.listId,
                    list1.title,
                    list1.system,
                    quoteFinder.convertList(quotes)
                )
            )
        }
        return newLists
    }

    /** Get all Lists but not their quotes */
    suspend fun getAllNoQuotes(): List<QuoteList> {
        val newLists = mutableListOf<QuoteList>()
        for (list in database.lists().getAll()) {
            newLists.add(
                QuoteList(
                    list.list.listId,
                    list.list.title,
                    count(list.list.listId),
                    list.list.system,
                    listIconManager.associate(list.list.icon)
                )
            )
        }
        return newLists
    }

    /** Add a new list */
    suspend fun newList(title: String) {
        database.lists().newList(Db.QuoteList(0, title, false, 0))
    }

    /** Update a list's icon */
    suspend fun updateIcon(listId: Int, iconId: Int) {
        database.lists().updateIcon(listId, iconId)
    }

    /** Update a list's icon */
    suspend fun addQuote(listId: Int, quoteId: Int) {
        database.lists().addQuote(listId, quoteId)
    }

    /** Delete a quote from a list */
    suspend fun removeQuote(listId: Int, quoteId: Int) {
        database.lists().deleteQuote(listId, quoteId)
    }

    /** Count the elements of a list **/
    suspend fun count(listId: Int): Int {
        return database.lists().count(listId)
    }
}