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

package org.bandev.buddhaquotes.core

import android.content.Context
import java.util.*

/**
 * The Lists class manages the Lists the user can create on their device. It stores the a [List] of
 * the users list, encoded as a [String] in shared preferences with the key "MASTER_LIST".
 *
 * @author jack.txt
 * @since v1.6.0
 * @updated 10/12/2020
 */

@Deprecated("This is deprecated use Database instead")
open class Lists {

    /**
     * Adds a quote to the list passed in
     * @param [quote] the quote to be added (String)
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     */

    fun addToList(quote: String, name: String, context: Context) {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString(name, "")
        val listArrTemp: MutableList<String> = (listArr?.split("//") ?: return).toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        listArrFinal.push(quote)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(name, stringOut)
        editor.apply()
    }

}