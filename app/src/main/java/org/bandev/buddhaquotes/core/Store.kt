package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences

class Store(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences("Store", 0)
    private var editor: SharedPreferences.Editor = sharedPref.edit()

    var fragment: Int
        get() = sharedPref.getInt("fragment", Fragments.QUOTE)
        set(value) {
            editor.putInt("fragment", value)
            editor.commit()
        }

    var quoteID: Int
        get() = sharedPref.getInt("quoteID", 0)
        set(value) {
            editor.putInt("quoteID", value)
            editor.commit()
        }
}