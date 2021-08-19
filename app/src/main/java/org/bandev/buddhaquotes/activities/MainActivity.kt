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
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.core.TopStyle
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.LottieAnimationRequest
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Bus
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Bars.updateNavbarColour
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.custom.BuddhaQuotesActivity
import org.bandev.buddhaquotes.databinding.ActivityMainBinding

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 */

class MainActivity: BuddhaQuotesActivity(), Bus.Listener {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: ViewModel
    private lateinit var navController: NavController
    private var menu: Menu? = null
    private var bus: Bus? = null
    private var tabSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            statusBarColor = TRANSPARENT
            updateNavbarColour()
            setDarkStatusIcons()
            navigationBarColor = getColor(context, R.color.abbBackgroundColor)
            setDecorFitsSystemWindows(this, false)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ViewModel::class.java)

        bus = Bus(this, "MainActivity").apply { listen() }

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (intent.extras?.getBoolean("languageChange") == true) navController.navigate(R.id.navigation_settings)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.apply {
            setSupportActionBar(this)
            applyInsets(InsetType.STATUS_BARS)
            setNavigationOnClickListener { if (binding.drawerLayout.isOpen) binding.drawerLayout.close() else binding.drawerLayout.open() }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.setTitle(
                when(destination.id) {
                    R.id.navigation_home -> R.string.app_name
                    R.id.navigation_settings -> R.string.settings
                    R.id.navigation_about -> R.string.about_app
                    else -> R.string.app_name
                }
            )
            if (menu != null) menu!!.findItem(R.id.help).isVisible = destination.id == R.id.navigation_home
        }

        binding.navigationView.apply {
            setupWithNavController(navController)
            setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true

                when (menuItem.itemId) {
                    R.id.quote -> {
                        if (navController.currentDestination!!.id != R.id.navigation_home) navController.popBackStack()
                        GlobalBus.post(Message(MessageType.NOTIFY_BOTTOMBAR, 0))
                    }
                    R.id.lists -> {
                        if (navController.currentDestination!!.id != R.id.navigation_home) navController.popBackStack()
                        GlobalBus.post(Message(MessageType.NOTIFY_BOTTOMBAR, 1))
                    }
                    R.id.meditate -> {
                        if (navController.currentDestination!!.id != R.id.navigation_home) navController.popBackStack()
                        GlobalBus.post(Message(MessageType.NOTIFY_BOTTOMBAR, 2))
                    }
                    R.id.settings -> {
                        if (navController.currentDestination!!.id == R.id.navigation_about) navController.popBackStack()
                        if (navController.currentDestination!!.id != R.id.navigation_settings) navController.navigate(R.id.navigation_settings)
                    }
                    R.id.about -> {
                        if (navController.currentDestination!!.id == R.id.navigation_settings) navController.popBackStack()
                        if (navController.currentDestination!!.id != R.id.navigation_about) navController.navigate(R.id.navigation_about)
                    }
                }

                binding.drawerLayout.close()
                true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        menuInflater.inflate(R.menu.help_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isEnabled = false
        var title = 0
        var content = 0
        var animation = 0
        var repeatCount = 0
        if (navController.currentDestination!!.id == R.id.navigation_home) {
            when (tabSelected) {
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

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isOpen -> binding.drawerLayout.close()
            navController.currentDestination!!.id == R.id.navigation_home -> if (tabSelected != 0) GlobalBus.post(Message(MessageType.NOTIFY_BOTTOMBAR, 0)) else super.onBackPressed()
            else -> super.onBackPressed()
        }
    }

    override fun onMessageReceived(message: Message<*>) {
        when(message.type) {
            MessageType.NOTIFY_TAB_INDEX -> {
                tabSelected = message.data as Int
                when(message.data) {
                    0 -> R.id.quote
                    1 -> R.id.lists
                    2 -> R.id.meditate
                    else -> null
                }?.let { binding.navigationView.setCheckedItem(it) }
            }
            else -> null
        }
    }

}
