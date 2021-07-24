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

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import dev.chrisbanes.insetter.applyInsetter
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.core.resolveColorAttr
import org.bandev.buddhaquotes.core.setAccentColour
import org.bandev.buddhaquotes.core.setDarkStatusIcons
import org.bandev.buddhaquotes.core.setNavigationBarColourMain
import org.bandev.buddhaquotes.databinding.ActivityMainBinding

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 */

class MainActivity : LocalizationActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            statusBarColor = Color.TRANSPARENT
            setNavigationBarColourMain()
            setDarkStatusIcons()
        }
        setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        with(binding) {
            with(toolbar) {
                applyInsetter {
                    type(statusBars = true) {
                        margin(top = true)
                    }
                }
                setNavigationOnClickListener { binding.root.open() }
            }

            viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

            with(navigationView) {
                setNavigationItemSelectedListener { menuItem ->
                    menuItem.isChecked = true

                    when (menuItem.itemId) {
                        R.id.quote -> binding.bottomBar.selectTabAt(0)
                        R.id.lists -> binding.bottomBar.selectTabAt(1)
                        R.id.meditate -> binding.bottomBar.selectTabAt(2)
                        R.id.settings -> startActivity(Intent(context, SettingsActivity::class.java))
                        R.id.about -> startActivity(Intent(context, AboutActivity::class.java))
                    }

                    binding.root.close()
                    true
                }
            }

            with(bottomBar) {
                applyInsetter {
                    type(navigationBars = true) {
                        margin(bottom = true)
                    }
                }
                setupWithViewPager2(binding.viewPager)
                onTabSelected = {
                    when (it) {
                        binding.bottomBar.tabs[0] -> binding.navigationView.setCheckedItem(R.id.quote)
                        binding.bottomBar.tabs[1] -> binding.navigationView.setCheckedItem(R.id.lists)
                        binding.bottomBar.tabs[2] -> binding.navigationView.setCheckedItem(R.id.meditate)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setAccentColour(this)
        with(binding.bottomBar) {
            tabColorSelected = context.resolveColorAttr(R.attr.colorPrimary)
            indicatorColor = context.resolveColorAttr(R.attr.colorPrimary)
        }
    }

    override fun onBackPressed() {
        when {
            binding.root.isOpen -> binding.root.close()
            binding.bottomBar.selectedIndex != 0 -> binding.bottomBar.selectTabAt(0)
            else -> super.onBackPressed()
        }
    }
}
