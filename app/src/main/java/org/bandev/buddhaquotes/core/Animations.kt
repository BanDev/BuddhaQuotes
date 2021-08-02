package org.bandev.buddhaquotes.core

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

class Animations {

    fun toolbarShadowScroll(scroll: RecyclerView, toolbar: AppBarLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll.setOnScrollChangeListener { _, _, _, _, _ ->
                toolbar.isSelected = scroll.canScrollVertically(-1)
            }
        }
    }
}