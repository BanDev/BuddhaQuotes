package org.bandev.buddhaquotes.architecture

/**
 * Buddha Quotes
 * Copyright (C) 2021  BanDev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import android.app.Application
import android.util.Log
import org.bandev.buddhaquotes.items.Quote

/**
 * Repository acts as a level of
 * abstraction when making db requests.
 */

class Repository(application: Application) {
    private val db = Db.getInstance(application.applicationContext)!!
    private val qf = QuoteFinder()

    /** Get a Quote using its key */
    suspend fun get(id: Int): Quote {
        val dbq = db.quotes().get(id)
        return Quote(dbq.id, qf.resource(dbq.id), dbq.like)
    }

    /** Get all Quotes */
    suspend fun getAll(): List<Quote> {
        val dbqs = db.quotes().getAll()
        val list = mutableListOf<Quote>()
        for ((id, like) in dbqs) list.add(Quote(id, qf.resource(id), like))
        return list
    }
}