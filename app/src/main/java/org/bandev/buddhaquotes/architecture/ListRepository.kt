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
import org.bandev.buddhaquotes.items.QuoteList
import org.bandev.buddhaquotes.items.QuoteListWithQuotes

/**
 * ListRepository acts as a level of
 * abstraction when making db requests
 * for list objects.
 */

class ListRepository(application: Application) {
    private val database = Db.getInstance(application.applicationContext)!!.lists()
    private val quoteFinder = QuoteFinder()

    /** Get a List & Quotes using its key */
    suspend fun get(id: Int): QuoteListWithQuotes {
        val list = database.get(id)
        return QuoteListWithQuotes(
            list.list.listId, list.list.title,
            list.list.system, quoteFinder.convertList(list.quotes)
        )
    }

    /** Get all Lists and their quotes */
    suspend fun getAll(): List<QuoteListWithQuotes> {
        val newLists = mutableListOf<QuoteListWithQuotes>()
        for (list in database.getAll()) {
            newLists.add(
                QuoteListWithQuotes(
                    list.list.listId,
                    list.list.title,
                    list.list.system,
                    quoteFinder.convertList(list.quotes)
                )
            )
        }
        return newLists
    }

    /** Get all Lists but not their quotes */
    suspend fun getAllNoQuotes(): List<QuoteList> {
        val newLists = mutableListOf<QuoteList>()
        for (list in database.getAll()) {
            newLists.add(
                QuoteList(
                    list.list.listId,
                    list.list.title,
                    list.list.system
                )
            )
        }
        return newLists
    }
}