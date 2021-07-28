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
import android.content.SharedPreferences

@Deprecated("This is deprecated use Database instead")
class ListsV2(context: Context) {

    private var sharedPrefs: SharedPreferences = context.getSharedPreferences("ListV2", 0)
    private var editor: SharedPreferences.Editor = sharedPrefs.edit()

    /**
     * Get a list of quotes
     * @param name [String] - The name of the list
     * @return [List<Int>] - The list of QuoteID's
     * @author Jack Devey
     * @since v2.2.0 (01/03/2021)
     */

    private fun getList(name: String): List<Int> {
        val listStr = sharedPrefs.getString(name, "-1")
        val listStrTmp: MutableList<String> = listStr!!.split("//").toMutableList()
        val intList: MutableList<Int> = mutableListOf()
        for (s in listStrTmp) intList.add(Integer.valueOf(s))
        return intList
    }

    /**
     * Saves a list
     * @param [list] the integer list (String)
     * @param [name] the name of the list (String)
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    private fun saveList(list: List<Int>, name: String) {
        val stringOut = list.joinToString(separator = "//")
        with(editor) {
            putString(name, stringOut)
            apply()
        }
    }

    /**
     * Adds a quote to a list
     * @param quote [Int] - The quote integer to be added
     * @param name [String] - The name of the list
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    fun addToList(quote: Int, name: String) {
        val list: MutableList<Int> = getList(name).toMutableList()
        list.add(0, quote)
        saveList(list, name)
    }

}