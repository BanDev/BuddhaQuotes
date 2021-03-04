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

@Deprecated("This is deprecated use Listsv2 instead")
open class Lists {

    /**
     * Sets activity's theme based off setting from preferences
     * @param [context] context of activity (Context)
     * @return List of the names of the lists on the users device (List)
     */

    fun getMasterList(context: Context): List<String> {
        val pref = context.getSharedPreferences("List_system", 0)
        val listArr = pref.getString("MASTER_LIST", "Favourites")
        val listArrFinal: MutableList<String> = listArr?.split("//")!!.toMutableList()
        return LinkedList(listArrFinal)
    }

    /**
     * Creates a new list with the name passed in
     * @param [name] the name of the new list (String)
     * @param [context] context of activity (Context)
     */

    fun newList(name: String, context: Context) {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        editor.putString(name, "null")
        editor.putString(
            "MASTER_LIST",
            (pref.getString("MASTER_LIST", "Favourites") + "//" + name)
        )
        editor.apply()
    }

    /**
     * Removes a list with the name passed in
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     */

    fun removeList(name: String, context: Context) {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString("MASTER_LIST", "Favourites")
        val listArrTemp: MutableList<String> = (listArr?.split("//") ?: return).toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        listArrFinal.remove(name)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString("MASTER_LIST", stringOut)
        editor.apply()
    }

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

    /**
     * Toggles the quote in the list, if it already exists it will be removed
     * @param [quote] the quote (String)
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     * @return
     */

    fun toggleInList(quote: String, name: String, context: Context): Boolean {
        val pref = context.getSharedPreferences("List_system", 0)
        val listArr = pref.getString(name, "")
        val listArrTemp: MutableList<String> = listArr?.split("//")!!.toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        return if (listArrFinal.contains(quote)) {
            removeFromList(quote, name, context)
            false
        } else {
            addToList(quote, name, context)
            true
        }
    }

    /**
     * Checks if the quote is inside the list already
     * @param [quote] the quote to be queried (String)
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     * @return true if quote is already added, false if it isn't (Boolean)
     */

    fun queryInList(quote: String, name: String, context: Context?): Boolean {
        val pref = context?.getSharedPreferences("List_system", 0)
        val listArr = pref?.getString(name, "")
        val listArrTemp: MutableList<String> = listArr?.split("//")!!.toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        return listArrFinal.contains(quote)
    }

    /**
     * Removes a quote from a list
     * @param [quote] the quote to be queried (String)
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     */

    private fun removeFromList(quote: String, name: String, context: Context) {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString(name, "")
        val listArrTemp: MutableList<String> = (listArr?.split("//") ?: return).toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        listArrFinal.remove(quote)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(name, stringOut)
        editor.apply()
    }

    fun getList(name: String, context: Context): List<String> {
        val pref = context.getSharedPreferences("List_system", 0)
        val prefString = pref.getString(name, "")
        val prefListTmp: MutableList<String> = prefString!!.split("//").toMutableList()
        prefListTmp.remove("null")
        return prefListTmp
    }

}