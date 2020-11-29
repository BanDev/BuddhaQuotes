package org.bandev.buddhaquotes.core

import android.content.Context
import java.util.*

class Lists {

    fun getMasterList(context: Context): List<String> {
        val pref = context.getSharedPreferences("List_system", 0)
        val listArr = pref.getString("MASTER_LIST", "Favourites")
        return LinkedList(listArr?.split("//"))
    }

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

    fun addToList(quote: String, name: String, context: Context): Boolean {
        val pref = context.getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString(name, "")
        val listArrFinal = LinkedList(listArr?.split("//"))
        if (listArrFinal.contains(quote)) {
            return false
        }
        listArrFinal.push(quote)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(name, stringOut)
        editor.apply()
        return true
    }

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