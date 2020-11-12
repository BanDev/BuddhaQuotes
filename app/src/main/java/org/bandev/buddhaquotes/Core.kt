package org.bandev.buddhaquotes

import android.content.Context
import java.util.*

/**
 * Core Library for org.bandev.buddhaquotes
 * @param [context] context of activity
 */

class Core(val context: Context) {

    /**
     * Manages & Creates 'Lists' (structured ways of storing masses of auotes)
     */

    inner class Lists {

        val quote: Quotes = Quotes()


        /**
         * Updates a 'list' (adds a quote to a pre-existing 'list')
         * @param [list] title of list (String)
         * @param [quote] text of quote (String)
         * @author jack.txt
         */

        fun newItemString(list: String, quote: String) {
            val pref = context.getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            val listArr = pref.getString(list, "")
            val listArrFinal = LinkedList(listArr?.split("//"))
            listArrFinal.push(quote)
            val stringOut = listArrFinal.joinToString(separator = "//")
            editor.putString(list, stringOut)
            editor.apply()
        }

    }

}