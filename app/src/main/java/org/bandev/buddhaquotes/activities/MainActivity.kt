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
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.util.addStickyDrawerItems
import com.mikepenz.materialdrawer.util.getDrawerItem
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityMainBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 * @since v1.0.0
 * @author Jack Devey & Fennec_exe
 */

class MainActivity : LocalizationActivity(), CustomInsets {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var icons: Icons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            setNavigationBarColourMain(context)
            setDarkStatusIcons(context)
            fitSystemBars(this@MainActivity)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        icons = Icons(this)

        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = icons.menu()
            setNavigationOnClickListener { onBackPressed() }
        }

        // Setup viewPager with FragmentAdapter
        with(binding.viewPager) {
            adapter = FragmentAdapter(supportFragmentManager, lifecycle)
            setCurrentItem(0, false)
        }

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
                headerBackground = ImageHolder(R.color.colorPrimary)
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
                PrimaryDrawerItem().apply {
                    nameRes = R.string.start_meditation; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_timer; isSelectable =
                    false; identifier = 4
                })
            addStickyDrawerItems(
                PrimaryDrawerItem().apply {
                    nameRes = R.string.settings; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_settings; isSelectable = false; identifier = 5
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.about; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_info; isSelectable = false; identifier = 6
                }
            )
            setSavedInstance(savedInstanceState)
        }

        var intent: Intent? = null
        binding.slider.onDrawerItemClickListener = { _, drawerItem, _ ->
            when (drawerItem.identifier) {
                1L -> binding.bottomBar.selectTabAt(0)
                2L -> binding.bottomBar.selectTabAt(1)
                3L -> binding.bottomBar.selectTabAt(2)
                4L -> {
                    binding.bottomBar.selectTabAt(2)
                    binding.slider.getDrawerItem(4L)!!.isEnabled = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        EventBus.getDefault().post(Event.StartMeditationTimer)
                    }, 200)
                }
                5L -> intent = Intent(this, SettingsActivity::class.java)
                6L -> intent = Intent(this, AboutActivity::class.java)
                else -> binding.root.closeDrawer(binding.slider)
            }
            if (intent != null) startActivity(intent)
            intent = null

            false
        }
    }

    @Subscribe
    fun onEventReceive(event: Event) {
        if (event is Event.RestoreDrawerButton) {
            binding.root.closeDrawer(binding.slider)
            (binding.slider.getDrawerItem(4L) ?: return).isEnabled = true
        }
    }

    @Suppress("DEPRECATION")
    override fun setCustomInsets(insets: WindowInsetsCompat) {
        val bottomInsets: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val ins = insets.getInsets(WindowInsets.Type.systemBars())
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
        super.onResume()
        setAccentColour(this)
        binding.root.closeDrawer(binding.slider)
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
        when {
            binding.root.isDrawerOpen(binding.slider) -> binding.root.closeDrawer(binding.slider)
            binding.bottomBar.selectedIndex != 0 -> binding.bottomBar.selectTabAt(0)
            else -> super.onBackPressed()
        }
    }
}
