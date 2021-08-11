package org.bandev.buddhaquotes.core

import android.view.View
import dev.chrisbanes.insetter.applyInsetter

object Insets {
    fun View.applyInsets(type: InsetType, option: InsetOption = InsetOption.MARGIN) {
        this.applyInsetter {
            if (type == InsetType.NAVIGATION_BARS) {
                type(navigationBars = true) {
                    if (option == InsetOption.PADDING) padding(bottom = true)
                    else margin(bottom = true)
                }
            } else if (type == InsetType.STATUS_BARS) {
                type(statusBars = true) {
                    if (option == InsetOption.PADDING) padding(top = true)
                    else margin(top = true)
                }
            }
        }
    }
}