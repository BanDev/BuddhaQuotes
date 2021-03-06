package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class ListsV2(context: Context) {

    var sharedPrefs: SharedPreferences = context.getSharedPreferences("ListV2", 0)
    var editor: SharedPreferences.Editor = sharedPrefs.edit()

    /**
     * Get a list of quotes
     * @param name [String] - The name of the list
     * @return [List<Int>] - The list of QuoteID's
     * @author Jack Devey
     * @since v2.2.0 (01/03/2021)
     */

    fun getList(name: String): List<Int> {
        val listStr = sharedPrefs.getString(name, "-1")
        val listStrTmp: MutableList<String> = listStr!!.split("//").toMutableList()
        val intList: MutableList<Int> = mutableListOf()
        for (s in listStrTmp) intList.add(Integer.valueOf(s))
        return intList
    }

    /**
     * Creates a new list with the name provided
     * @param name [String] - The name of the list
     * @param data [List] - The data of the list
     * @return [Boolean] - True if data was saved, false if not
     * @author Jack Devey
     * @since v2.2.0 (01/03/2021)
     */

    fun newList(name: String, data: List<Int>) {
        val current = sharedPrefs.getString("MASTER_LIST", "Favourites")
        val lists = getMasterList()
        if (!lists.contains(name)) editor.putString("MASTER_LIST", "$current//$name")
        editor.putString(name, data.joinToString(separator = "//"))
        editor.commit()
    }

    /**
     * Removes a whole list
     * @param name [String] - The name of the list
     * @author Jack Devey
     * @since v2.2.0 (01/03/2021)
     */

    fun removeList(name: String) {
        val listArr = sharedPrefs.getString("MASTER_LIST", "Favourites")
        val listArrTemp: MutableList<String> = (listArr?.split("//") ?: return).toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        listArrFinal.remove(name)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.remove(name)
        editor.putString("MASTER_LIST", stringOut)
        editor.apply()
    }

    /**
     * Creates a new empty list
     * @param name [String] - The name of the list
     * @author Jack Devey
     * @since v2.2.0 (01/03/2021)
     */

    fun newEmptyList(name: String) {
        val current = sharedPrefs.getString("MASTER_LIST", "Favourites")
        editor.putString(name, "-1")
        editor.putString("MASTER_LIST", "$current//$name")
        editor.commit()
    }

    /**
     * Checks if the quote is inside the list already
     * @param [quote] the quote to be queried (String)
     * @param [name] the name of the list (String)
     * @return true if quote is already added, false if it isn't (Boolean)
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    fun queryInList(quote: Int, name: String): Boolean = getList(name).contains(quote)

    /**
     * Saves a list
     * @param [list] the integer list (String)
     * @param [name] the name of the list (String)
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    private fun saveList(list: List<Int>, name: String) {
        val stringOut = list.joinToString(separator = "//")
        editor.putString(name, stringOut)
        editor.apply()
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

    /**
     * Removes a quote from a list
     * @param quote [Int] - The quote integer to be added
     * @param name [String] - The name of the list
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    fun removeFromList(quote: Int, name: String) {
        val list: MutableList<Int> = getList(name).toMutableList()
        list.remove(quote)
        saveList(list, name)
    }

    /**
     * Gets the list of all the lists on the app
     * @return [List] of all the lists <String>
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    fun getMasterList(): List<String> {
        val listArr = sharedPrefs.getString("MASTER_LIST", "Favourites")
        val listArrFinal: MutableList<String> = listArr?.split("//")!!.toMutableList()
        return LinkedList(listArrFinal)
    }

    /**
     * Toggles the quote in the list, if it already exists it will be removed
     * @param quote [Int] the integer id of the quote
     * @param name [String] the name of the list
     * @return [Boolean] True if was added, False if deleted
     * @author Jack Devey
     * @since v2.2.0 (02/03/2021)
     */

    fun toggleInList(quote: Int, name: String): Boolean {
        val lists: List<Int> = getList(name)
        return if (lists.contains(quote)) {
            removeFromList(quote, name)
            false
        } else {
            addToList(quote, name)
            true
        }
    }
}