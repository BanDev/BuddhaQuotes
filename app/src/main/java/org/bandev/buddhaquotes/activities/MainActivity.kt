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
import android.content.res.ColorStateList.valueOf
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.core.TopStyle
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.LottieAnimationRequest.Companion.INFINITE
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Insets.NAVIGATION_BARS
import org.bandev.buddhaquotes.core.Insets.STATUS_BARS
import org.bandev.buddhaquotes.core.Insets.applyInsets
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
    private lateinit var model: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            statusBarColor = TRANSPARENT
            navigationBarColor = getColor(context, R.color.abbBackgroundColor)
            setDarkStatusIcons()
            setDecorFitsSystemWindows(this, false)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ViewModel::class.java)

        with(binding) {
            toolbar.apply {
                setSupportActionBar(this)
                applyInsets(STATUS_BARS)
                setNavigationOnClickListener { binding.root.open() }
            }

            viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

            navigationView.apply {
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

            createList.apply {
                backgroundTintList = valueOf(resolveColorAttr(R.attr.colorAccent))
                setOnClickListener {
                    Feedback.virtualKey(it)
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
                                        GlobalBus.post(Message(MessageTypes.NEW_LIST, list))
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
                applyInsets(NAVIGATION_BARS)
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
                repeatCount = INFINITE
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
        return true
    }

    override fun onResume() {
        super.onResume()
        setAccentColour()
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
            binding.root.isOpen -> binding.root.close()
            binding.bottomBar.selectedIndex != 0 -> binding.bottomBar.selectTabAt(0)
            else -> super.onBackPressed()
        }
    }
}
