package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class ListsV2(context: Context) {

    var sharedPrefs: SharedPreferences = context.getSharedPreferences("ListV2", 0)
    var editor: SharedPreferences.Editor = sharedPrefs.edit()

    /**
     * Get a list of quotes
     * @param name [String] - The name of the list
     * @return [List<Int>] - The list of QuoteID's
     * @author Jack Devey
     * @since v2.2.0 (01/3/2021)
     */

    fun getList(name: String): List<Int> {
        val listStr = sharedPrefs.getString(name, "")
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
     * @since v2.2.0 (01/3/2021)
     */

    fun newList(name: String, data: List<Int>) {
        val current = sharedPrefs.getString("MASTER_LIST", "Favourites")
        editor.putString(name, data.joinToString(separator = "//"))
        editor.putString("MASTER_LIST", "$current//$name")
        editor.commit()
    }

    fun debug() {
        val masterList = sharedPrefs.getString("MASTER_LIST", "Favourites")
        val listStrTmp: MutableList<String> = (masterList ?: return).split("//").toMutableList()
        Log.i("ListDebug", listStrTmp.joinToString("//"))
        for (list in listStrTmp) {
            Log.i("ListDebug", "Start of $list")
            for (id in getList(list)) Log.i(list, id.toString())
        }
    }
}