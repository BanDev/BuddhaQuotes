package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences

class TimerStore(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences("TimerStore", 0)
    private var editor: SharedPreferences.Editor = sharedPref.edit()

    var active: Boolean
        get() = sharedPref.getBoolean("active", false)
        set(value) {
            editor.putBoolean("active", value)
            editor.commit()
        }

    var duration: Long
        get() = sharedPref.getLong("duration", 0L)
        set(value) {
            editor.putLong("duration", value)
            editor.commit()
        }

    var progress: Long
        get() = sharedPref.getLong("progress", 0L)
        set(value) {
            editor.putLong("progress", value)
            editor.commit()
        }

    fun resumeTimers(): Boolean {
        return (duration != 0L && progress != 0L && active)
    }
}