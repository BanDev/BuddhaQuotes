package org.bandev.buddhaquotes.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.bandev.buddhaquotes.SampleFragment

/**
 * Fragment adapter for MainActivity
 * @param fragmentManager [FragmentManager]
 * @param lifecycle [Lifecycle]
 * @author jack.txt
 * @since 1.7.0
 */

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentCount = 3

    /**
     * Returns the count of fragments avaliable
     * @return [Int]
     */

    override fun getItemCount(): Int {
        return fragmentCount
    }

    /**
     * Show the correct fragment based on the position
     * @param position [Int]
     * @return [Fragment]
     */

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QuoteFragment.newInstance(position)
            else -> SampleFragment.newInstance(position)
        }
    }
}