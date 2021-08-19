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

package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akexorcist.localizationactivity.core.LanguageSetting.getLanguage
import com.akexorcist.localizationactivity.core.LanguageSetting.setLanguage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maxkeppeler.sheets.color.ColorSheet
import com.maxkeppeler.sheets.color.ColorView
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.FragmentAboutBinding
import org.bandev.buddhaquotes.databinding.FragmentSettingsBinding
import java.util.*

/**
 * Where the user can customise their app
 */
class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = AboutStateAdapter(requireActivity())
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
                Companion.POS_ABOUT -> AboutFragmentChild()
                Companion.POS_LICENSE -> LibrariesFragment()
                else -> throw IllegalArgumentException("Unknown position for ViewPager2")
            }
        }

        override fun getItemCount(): Int = Companion.TOTAL_COUNT
    }

    companion object {
        private const val POS_ABOUT = 0
        private const val POS_LICENSE = 1
        private const val TOTAL_COUNT = 2
    }
}
