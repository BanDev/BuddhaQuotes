package org.bandev.buddhaquotes.custom

import android.view.View

/**
 * onDoubleClickListener extends onClickListener but allows for detecting double clicks. To change
 * click time delay change [DOUBLE_CLICK_TIME_DELTA] variable.
 *
 * @author jack.txt
 * @since v1.7.0
 * @updated 09/12/2020
 */

abstract class OnDoubleClickListener : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v)
            lastClickTime = 0
        }
        lastClickTime = clickTime
    }

    abstract fun onDoubleClick(v: View?)

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
    }
}