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
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.ListIcons
import org.bandev.buddhaquotes.core.defaultIcon
import org.bandev.buddhaquotes.items.QuoteList
import org.bandev.buddhaquotes.items.QuoteListWithQuotes

/**
 * ListRepository acts as a level of
 * abstraction when making db requests
 * for list objects.
 */

class ListRepository(val application: Application) {
    private val database = Db.getInstance(application.applicationContext)!!
    private val quoteFinder = QuoteFinder()

    /** Get a List & Quotes using its key */
    suspend fun get(id: Int): QuoteListWithQuotes {
        if (id == 0) return getFavourites()
        val list = database.lists().get(id)
        return QuoteListWithQuotes(
            list.list.listId, list.list.title,
            list.list.system, quoteFinder.convertList(list.quotes)
        )
    }

    /** Get the favourites list */
    private suspend fun getFavourites(): QuoteListWithQuotes {
        return QuoteListWithQuotes(
            0, application.getString(R.string.favourites),
            true, quoteFinder.convertList(database.quotes().getAllFavourites())
        )
    }

    /** Get the favourites list with no quotes */
    private fun getFavouritesNoQuotes(): QuoteList {
        return QuoteList(
            0, application.getString(R.string.favourites),
            true,
            defaultIcon(application.applicationContext)
        )
    }

    /** Get all Lists and their quotes */
    suspend fun getAll(): List<QuoteListWithQuotes> {
        val newLists = mutableListOf<QuoteListWithQuotes>()
        newLists.add(getFavourites())
        for (list in database.lists().getAll()) {
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
        newLists.add(getFavouritesNoQuotes())
        for (list in database.lists().getAll()) {
            newLists.add(
                QuoteList(
                    list.list.listId,
                    list.list.title,
                    list.list.system,
                    ListIcons(application.applicationContext)[list.list.icon]
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
}