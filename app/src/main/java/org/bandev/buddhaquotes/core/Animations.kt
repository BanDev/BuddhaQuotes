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