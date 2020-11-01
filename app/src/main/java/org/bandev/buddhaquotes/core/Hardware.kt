package org.bandev.buddhaquotes.core

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class Hardware {

    /**
     * Vibrates the activity that calls it
     * @param [context] context of activity (Context)
     * @author Fennec_exe
     * @added [1008] v1.5.0 - 01/11/2020
     */

    fun vibrate(context: Context) {
        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib.vibrate(30)
        }
    }
}