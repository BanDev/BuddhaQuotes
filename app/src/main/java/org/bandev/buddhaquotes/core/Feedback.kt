package org.bandev.buddhaquotes.core

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View

object Feedback {

    fun confirm(view: View) {
        feedback(
            view, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                HapticFeedbackConstants.CONFIRM
            } else {
                HapticFeedbackConstants.VIRTUAL_KEY
            }
        )
    }

    fun reject(view: View) {
        feedback(
            view, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                HapticFeedbackConstants.REJECT
            } else {
                HapticFeedbackConstants.VIRTUAL_KEY
            }
        )
    }

    private fun feedback(view: View, constant: Int) {
        view.performHapticFeedback(constant)
    }

}