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

import org.bandev.buddhaquotes.custom.BuddhaQuotesActivity
import android.content.res.ColorStateList.valueOf
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.core.TopStyle
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.LottieAnimationRequest
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.databinding.ActivityMainBinding

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 */

class MainActivity: BuddhaQuotesActivity<ActivityMainBinding>() {

    private lateinit var model: ViewModel

    override fun create() {

        assignViewModel(ViewModel::class.java)
        assignToolbar(binding.toolbar)
        setNavBarColour(R.color.abbBackgroundColor)

        setupNavigationView(binding.drawerLayout, binding.navigationView) {
            when (it.itemId) {
                R.id.quote -> binding.bottomBar.selectTabAt(0)
                R.id.lists -> binding.bottomBar.selectTabAt(1)
                R.id.meditate -> binding.bottomBar.selectTabAt(2)
                R.id.settings -> open(SettingsActivity::class.java)
                R.id.about -> open(AboutActivity::class.java)
            }
        }

        with(binding) {

            viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

            createList.apply {
                backgroundTintList = valueOf(resolveColorAttr(R.attr.colorAccent))
                onClick {
                    it.isEnabled = false
                    InputSheet().show(context) {
                        title(R.string.create_new_list)
                        closeIconButton(IconButton(R.drawable.ic_down_arrow))
                        with(
                            InputEditText {
                                required()
                                hint(R.string.insert_name)
                                resultListener { value ->
                                    model.Lists().new(value.toString()) { list ->
                                        GlobalBus.post(Message(MessageType.NEW_LIST, list))
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

            bottomBar.apply {
                applyInsets(InsetType.NAVIGATION_BARS)
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

    override fun menuItemSelected(item: MenuItem) {
        item.isEnabled = false
        var title = 0
        var content = 0
        var animation = 0
        var repeatCount = 0
        when (binding.bottomBar.selectedIndex) {
            0 -> {
                title = R.string.quote_sheet_title
                content = R.string.quote_sheet_content
                animation = R.raw.flower
                repeatCount = 0
            }
            1 -> {
                title = R.string.list_sheet_title
                content = R.string.list_sheet_content
                animation = R.raw.lists
                repeatCount = 0
            }
            2 -> {
                title = R.string.meditate_sheet_title
                content = R.string.meditate_sheet_content
                animation = R.raw.meditation
                repeatCount = LottieAnimationRequest.INFINITE
            }
        }
        InfoSheet().show(this) {
            title(title)
            content(content)
            displayCloseButton(false)
            displayButtons(false)
            topStyle(TopStyle.BELOW_COVER)
            withCoverLottieAnimation(LottieAnimation {
                ratio(Ratio(5, 2))
                setupAnimation {
                    setAnimation(animation)
                    setRepeatCount(repeatCount)
                }
            })
            onClose { item.isEnabled = true }
        }
    }

    override fun resume() {
        with(binding) {
            createList.backgroundTintList = valueOf(resolveColorAttr(R.attr.colorAccent))
            bottomBar.apply {
                tabColorSelected = resolveColorAttr(R.attr.colorPrimary)
                indicatorColor = resolveColorAttr(R.attr.colorPrimary)
            }
        }
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isOpen -> binding.drawerLayout.close()
            binding.bottomBar.selectedIndex != 0 -> binding.bottomBar.selectTabAt(0)
            else -> super.onBackPressed()
        }
    }

    override val bInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate
}
