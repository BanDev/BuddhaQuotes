package org.bandev.buddhaquotes.core

import android.app.Activity
import android.content.Context

open class AccentDelegate(val activity: Activity) {

    fun setAccentColor(context: Context, accentColor: AccentColor) {
        AccentSetting.setAccentColor(context, accentColor)
    }

    fun getAccentColor(context: Context): AccentColor? {
        return AccentSetting.getAccentColor(context)
    }

}