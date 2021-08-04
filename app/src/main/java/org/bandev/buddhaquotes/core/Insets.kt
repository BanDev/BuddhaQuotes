package org.bandev.buddhaquotes.core

import android.view.View
import dev.chrisbanes.insetter.applyInsetter

object Insets {
    const val NAVIGATION_BARS: Int = 70
    const val STATUS_BARS: Int = 60
    const val PADDING: Int = 0
    const val MARGIN: Int = 1

    fun View.applyInsets(type: Int, option: Int = MARGIN) {
        this.applyInsetter {
            if (type == NAVIGATION_BARS) {
                type(navigationBars = true) {
                    if (option == PADDING) padding(bottom = true)
                    else margin(bottom = true)
                }
            } else if (type == STATUS_BARS) {
                type(statusBars = true) {
                    if (option == PADDING) padding(top = true)
                    else margin(top = true)
                }
            }
        }
    }
}