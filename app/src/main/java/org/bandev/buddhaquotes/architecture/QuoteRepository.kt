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
import org.bandev.buddhaquotes.items.Quote

/**
 * QuoteRepository acts as a level of
 * abstraction when making db requests
 * for quote objects.
 */

class QuoteRepository(application: Application) {
    private val database = Db.getInstance(application.applicationContext)!!.quotes()
    private val quoteFinder = QuoteFinder(this)

    /** Get a Quote using its key */
    suspend fun get(id: Int): Quote = quoteFinder.convert(database.get(id), isLiked(id))

    /** Get all Quotes */
    suspend fun getAll(): List<Quote> {
        val quotes = database.getAll()
        for (quote in quotes) {
            quote.like = isLiked(quote.quoteId)
        }
        return quoteFinder.convertList(quotes)
    }

    /** Count the number of quotes */
    suspend fun count(): Int = database.count()

    /** Define if a quote is liked or not */
    suspend fun setLike(id: Int, like: Boolean) {
        if (like) database.like(id)
        else database.removeLike(id)
    }

    /** See if a quote is liked or not */
    suspend fun isLiked(id: Int): Boolean = database.isLiked(id) == 1
}