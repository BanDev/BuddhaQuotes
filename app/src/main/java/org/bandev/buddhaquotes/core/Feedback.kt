/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

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

    fun virtualKey(view: View) {
        feedback(view, HapticFeedbackConstants.VIRTUAL_KEY)
    }

    fun clockTick(view: View) {
        feedback(view, HapticFeedbackConstants.CLOCK_TICK)
    }

    private fun feedback(view: View, constant: Int) {
        view.performHapticFeedback(constant)
    }

}