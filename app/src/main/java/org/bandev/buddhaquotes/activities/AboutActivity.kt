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

package org.bandev.buddhaquotes.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityAboutBinding
import org.bandev.buddhaquotes.fragments.AboutFragment
import org.bandev.buddhaquotes.fragments.LibrariesFragment

/**
 * The about page
 */
class AboutActivity : LocalizationActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = context.backIcon()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        binding.tabLayout.setBackgroundColor(toolbarColour(this))

        // Set up the ViewPager with the sections adapter
        binding.viewPager.adapter = AboutStateAdapter(this)
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                POS_ABOUT -> tab.setText(R.string.about)
                POS_LICENSE -> tab.setText(R.string.libraries)
                else -> throw IllegalArgumentException("Unknown position for ViewPager2")
            }
        }.attach()
    }

    /**
     * A [FragmentStateAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    class AboutStateAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                Companion.POS_ABOUT -> AboutFragment()
                Companion.POS_LICENSE -> LibrariesFragment()
                else -> throw IllegalArgumentException("Unknown position for ViewPager2")
            }
        }

        override fun getItemCount(): Int {
            return Companion.TOTAL_COUNT
        }
    }

    companion object {
        private const val POS_ABOUT = 0
        private const val POS_LICENSE = 1
        private const val TOTAL_COUNT = 2
    }
}
