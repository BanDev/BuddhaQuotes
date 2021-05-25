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
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.util.addStickyDrawerItems
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityMainBinding
import org.bandev.buddhaquotes.fragments.FragmentAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 * @since v1.0.0
 * @author jack.txt & Fennec_exe
 */

class MainActivity : LocalizationActivity(), CustomInsets {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    /**
     * On activity created
     *
     * @param savedInstanceState [Bundle]
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation bar
        window.setNavigationBarColourMain(this)

        // Setup view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitSystemBars(binding.root, window, this)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = context.hamburgerMenuIcon()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        // Setup viewPager with FragmentAdapter
        with(binding.viewPager) {
            adapter = FragmentAdapter(supportFragmentManager, lifecycle)
            setCurrentItem(0, false)
        }

        // Setup bottomBar with the viewpager
        binding.bottomBar.setupWithViewPager2(binding.viewPager)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.root,
            binding.toolbar,
            com.mikepenz.materialdrawer.R.string.material_drawer_open,
            com.mikepenz.materialdrawer.R.string.material_drawer_close
        )

        fun buildHeader(compact: Boolean, savedInstanceState: Bundle?) {
            headerView = AccountHeaderView(this, compact = compact).apply {
                attachToSliderView(binding.slider)
                headerBackground = ImageHolder(R.drawable.header)

                withSavedInstance(savedInstanceState)
            }
        }

        buildHeader(true, savedInstanceState)

        binding.slider.apply {
            itemAdapter.add(
                PrimaryDrawerItem().apply {
                    nameRes = R.string.fragment_quote; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_format_quote; isSelectable = false; identifier =
                    1
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.fragment_lists; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_list; isSelectable = false; identifier = 2
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.fragment_meditation; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_self_improvement; isSelectable =
                    false; identifier = 3
                },
                DividerDrawerItem(),
                PrimaryDrawerItem().apply {
                    nameRes = R.string.open_source_libraries; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_library_books; isSelectable = false; identifier =
                    6
                })
            addStickyDrawerItems(
                PrimaryDrawerItem().apply {
                    nameRes = R.string.settings; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_settings; isSelectable = false; identifier = 4
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.about; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_info; isSelectable = false; identifier = 5
                }
            )
            setSavedInstance(savedInstanceState)
        }

        var intent: Intent? = null
        binding.slider.onDrawerItemClickListener = { _, drawerItem, _ ->
            when (drawerItem.identifier) {
                1L -> binding.bottomBar.selectTabAt(0, true)
                2L -> binding.bottomBar.selectTabAt(1, true)
                3L -> binding.bottomBar.selectTabAt(2, true)
                4L -> intent =
                    Intent(this, SettingsActivity::class.java)
                5L -> intent = Intent(this, AboutActivity::class.java)
                6L -> intent = Intent(this, LibrariesActivity::class.java)
                else -> binding.slider.drawerLayout?.closeDrawer(binding.slider)
            }
            if (intent != null) startActivity(intent)
            intent = null

            false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNotifyReceive(event: Notify) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNotifyReceive(event: SendEvent.ToListFragment) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                showCreateListSheet()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * On options menu item selected
     * @return [Boolean]
     */

    // Build the input bottom sheet that allows creation of a new list
    private fun showCreateListSheet() {
        // Retrieve the lists
        val lists = ListsV2(this).getMasterList()
        val closeDrawable = IconicsDrawable(
            this,
            RoundedGoogleMaterial.Icon.gmr_expand_more
        ).apply {
            sizeDp = 24
            paddingDp = 6
        }

        InputSheet().show(this) {
            title(R.string.createNewList)
            closeIconButton(IconButton(closeDrawable))
            with(
                InputEditText {
                    required()
                    hint(R.string.insertName)
                    validationListener { value ->
                        when {
                            value.contains("//") -> Validation.failed(getString(R.string.validationRule1))
                            value.toLowerCase(Locale.ROOT) == "favourites" -> Validation.failed(
                                getString(R.string.validationRule2)
                            )
                            lists.contains(value.toLowerCase(Locale.ROOT)) -> Validation.failed(
                                getString(R.string.validationRule3) + " $value"
                            )
                            else -> Validation.success()
                        }
                    }
                    resultListener { value ->
                        EventBus.getDefault().post(Notify.NotifyNewList(value.toString()))
                    }
                }
            )
            onNegative { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
            onPositive(R.string.add) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) binding.root.performHapticFeedback(
                    HapticFeedbackConstants.CONFIRM
                )
                else binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun setCustomInsets(insets: WindowInsetsCompat) {
        val bottomInsets: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Get the insets for the system bars
            val ins = insets.getInsets(WindowInsets.Type.systemBars())
            // Set the padding for the bottom sheet to the navigation bar height
            with(binding) {
                toolbar.updatePadding(top = ins.top)
                bottomBar.updatePadding(bottom = ins.bottom)
                slider.updatePadding(bottom = ins.bottom)
            }
            bottomInsets = ins.bottom
        } else {
            with(binding) {
                toolbar.updatePadding(top = insets.systemWindowInsetTop)
                bottomBar.updatePadding(bottom = insets.systemWindowInsetBottom)
                slider.updatePadding(bottom = insets.systemWindowInsetBottom)
            }
            bottomInsets = insets.systemWindowInsetBottom
        }
        binding.bottomBar.minimumHeight = 170 + bottomInsets
    }

    override fun onResume() {
        // Set the colour of everything to the accent colour when the user returns to the Main Activity
        super.onResume()
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        binding.toolbar.setBackgroundColor(toolbarColour(this))
        with(binding.bottomBar) {
            tabColorSelected = context.resolveColorAttr(R.attr.colorPrimary)
            indicatorColor = context.resolveColorAttr(R.attr.colorPrimary)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        // If the drawer is open, close it. If it is already closed, exit the app
        if (binding.root.isDrawerOpen(binding.slider)) binding.root.closeDrawer(binding.slider) else super.onBackPressed()
    }
}
