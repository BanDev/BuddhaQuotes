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
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import dev.chrisbanes.insetter.applyInsetter
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.architecture.ListViewModel
import org.bandev.buddhaquotes.core.*
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
    private lateinit var model: ListViewModel

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

        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ListViewModel::class.java)


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

            with(createList) {
                backgroundTintList = ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))
                setOnClickListener {
                    Feedback.virtualKey(it)
                    it.isEnabled = false
                    InputSheet().show(context) {
                        title(R.string.createNewList)
                        closeIconButton(IconButton(R.drawable.ic_down_arrow))
                        with(
                            InputEditText {
                                required()
                                hint(R.string.insertName)
                                resultListener { value ->
                                    model.newList(value.toString()) {
                                        GlobalBus.post(UpdateLists())
                                    }
                                }
                            }
                        )
                        onNegative(R.string.cancel) { Feedback.virtualKey(binding.root) }
                        onPositive(R.string.add) { Feedback.confirm(binding.root) }
                        onClose { it.isEnabled = true }
                    }
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
                        binding.bottomBar.tabs[0] -> {
                            with(binding) {
                                createList.hide()
                                navigationView.setCheckedItem(R.id.quote)
                            }
                        }
                        binding.bottomBar.tabs[1] -> {
                            with(binding) {
                                createList.show()
                                navigationView.setCheckedItem(R.id.lists)
                            }
                        }
                        binding.bottomBar.tabs[2] -> {
                            with(binding) {
                                createList.hide()
                                navigationView.setCheckedItem(R.id.meditate)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.help_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.help -> {
                when (binding.bottomBar.selectedIndex) {
                    0 -> {
                        InfoSheet().show(this) {
                            title("Team Collaboration")
                            content("In the world of software projects, it is inevitable...")
                            closeIconButton(IconButton(R.drawable.ic_down_arrow))
                            displayButtons(false)
                            withCoverLottieAnimation(LottieAnimation {
                                ratio(Ratio(2, 1))
                                setupAnimation {
                                    setAnimation(R.raw.lotus)
                                }
                            })
                        }
                    }
                    1 -> {
                        InfoSheet().show(this) {
                            title("Title")
                            content("You can do this to meditate")
                            closeIconButton(IconButton(R.drawable.ic_down_arrow))
                            displayButtons(false)
                            withCoverLottieAnimation(LottieAnimation {
                                ratio(Ratio(2, 1))
                                setupAnimation {
                                    setAnimation(R.raw.lists)
                                }
                            })
                        }
                    }
                    2 -> {
                        InfoSheet().show(this) {
                            title("Title")
                            content("Content")
                            closeIconButton(IconButton(R.drawable.ic_down_arrow))
                            displayButtons(false)
                            withCoverLottieAnimation(LottieAnimation {
                                ratio(Ratio(2, 1))
                                setupAnimation {
                                    setAnimation(R.raw.meditation)
                                }
                            })
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        setAccentColour(this)
        with(binding) {
            createList.backgroundTintList = ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))
            with(bottomBar) {
                tabColorSelected = resolveColorAttr(R.attr.colorPrimary)
                indicatorColor = resolveColorAttr(R.attr.colorPrimary)
            }
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
