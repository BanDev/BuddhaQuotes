package org.bandev.buddhaquotes.core

import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object Insets {

    fun edgeToEdge(window: Window, after: (insets: WindowInsetsCompat) -> Unit) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            after(insets)
            insets
        }
    }

}