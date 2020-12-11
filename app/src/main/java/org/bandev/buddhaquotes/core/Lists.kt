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

class Lists {

    /**
     * Sets activity's theme based off setting from preferences
     * @param [context] context of activity (Context)
     * @return List of the names of the lists on the users device (List)
     */

    fun getMasterList(context: Context): List<String> {
        val pref = context.getSharedPreferences("List_system", 0)
        val listArr = pref.getString("MASTER_LIST", "Favourites")
        return LinkedList(listArr?.split("//"))
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
        val listArrFinal = LinkedList(listArr?.split("//"))
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
        val listArrFinal = LinkedList(listArr?.split("//"))
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
        val listArrFinal = LinkedList(listArr?.split("//"))
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
        val listArrFinal = LinkedList(listArr?.split("//"))
        return listArrFinal.contains(quote)
    }

    /**
     * Removes a quote from a list
     * @param [quote] the quote to be queried (String)
     * @param [name] the name of the list (String)
     * @param [context] context of activity (Context)
     */

    fun removeFromList(quote: String, name: String, context: Context) {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString(name, "")
        val listArrFinal = LinkedList(listArr?.split("//"))
        listArrFinal.remove(quote)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(name, stringOut)
        editor.apply()
    }

}