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

package org.bandev.buddhaquotes.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.bandev.buddhaquotes.fragments.ListsFragment
import org.bandev.buddhaquotes.fragments.QuoteFragment
import org.bandev.buddhaquotes.fragments.TimerFragment

/**
 * Fragment adapter for MainActivity
 * @param fragmentManager [FragmentManager]
 * @param lifecycle [Lifecycle]
 */

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentCount = 3

    /**
     * Returns the count of fragments avaliable
     * @return [Int]
     */

    override fun getItemCount(): Int = fragmentCount

    /**
     * Show the correct fragment based on the position
     * @param position [Int]
     * @return [Fragment]
     */

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QuoteFragment.newInstance(position)
            1 -> ListsFragment.newInstance(position)
            else -> TimerFragment.newInstance(position)
        }
    }
}
