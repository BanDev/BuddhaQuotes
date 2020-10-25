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

        val quote = Quotes()


        /**
         * Updates a 'list' (adds a quote to a pre-existing 'list')
         * @param [list] title of list (String)
         * @param [num] id of quote (Int)
         * @author jack.txt
         */

        fun newItem(list: String, num: Int) {
            val pref = context.getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            val list_arr = pref.getString(list, "")
            val list_arr_final = LinkedList(list_arr?.split("//"))
            list_arr_final.push(quote.random(num))
            val string_out = list_arr_final.joinToString(separator = "//")
            editor.putString(list, string_out)
            editor.commit()
        }


        /**
         * Updates a 'list' (removes a quote from a pre-existing 'list')
         * @param [list] title of list (String)
         * @param [num] id of quote (Int)
         * @author jack.txt
         */

        fun removeItem(list: String, num: Int) {
            val pref = context.getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            val list_arr = pref.getString(list, "")
            val list_arr_final = LinkedList(list_arr?.split("//"))
            list_arr_final.remove(quote.random(num))
            val string_out = list_arr_final.joinToString(separator = "//")
            editor.putString(list, string_out)
            editor.commit()
        }


        /**
         * Creates a 'List' (adds title to master list, generates a sub-list under title)
         * @param [text] title of new list (String)
         * @author jack.txt
         */

        fun newList(text: String) {
            val pref = context.getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            editor.putString(text, "")
            val list_arr = pref.getString("MASTER_LIST", "Favourites")
            val list_arr_final = LinkedList(list_arr?.split("//"))
            list_arr_final.add(text)
            val string_out = list_arr_final.joinToString(separator = "//")
            editor.putString("MASTER_LIST", string_out)
            editor.commit()
        }


        /**
         * Deletes a 'List' (removes title from master list, clears sub-list under title)
         * @param [text] title of list to be deleted (String)
         * @author jack.txt
         */

        fun removeList(text: String) {
            val pref = context.getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            editor.putString(text, "")
            val list_arr = pref.getString("MASTER_LIST", "Favourites")
            val list_arr_final = LinkedList(list_arr?.split("//"))
            list_arr_final.remove(text)
            val string_out = list_arr_final.joinToString(separator = "//")
            editor.putString("MASTER_LIST", string_out)
            editor.commit()
        }


        /**
         * Takes a 'list' and writes outputs it as string with seperators
         * @param [list] title of list (String)
         * @param [seperator] seperator for output (String)
         * @return list as text in readable format (String)
         * @author jack.txt
         */

        fun writeList(list: String, seperator: String): String {
            val pref = context.getSharedPreferences("List_system", 0)
            val list_arr = pref.getString(list, "")
            val list_arr_final = LinkedList(list_arr?.split("//"))
            val string_out = list_arr_final.joinToString(separator = seperator)
            return string_out
        }
        
    }

}