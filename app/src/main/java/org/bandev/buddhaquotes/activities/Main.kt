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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.descriptionRes
import com.mikepenz.materialdrawer.model.interfaces.iconDrawable
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.MainActivityBinding
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

class Main : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    /**
     * On activity created
     *
     * @param savedInstanceState [Bundle]
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup view binding
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourMain(this, window, resources)
        Languages(baseContext).setLanguage()

        val sharedPrefs = getSharedPreferences("Settings", 0)
        val editor = sharedPrefs.edit()

        if (sharedPrefs.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            startActivity(Intent(this, Intro::class.java))
        }

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.background = ContextCompat.getDrawable(this, R.drawable.toolbar)

        // Setup viewPager with FragmentAdapter
        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.setCurrentItem(0, false)
        binding.bottomBar.setupWithViewPager2(binding.viewPager)
        binding.bottomBar.tabColorSelected = Colours().getAccentColourAsInt(this)
        binding.bottomBar.indicatorColor = Colours().getAccentColourAsInt(this)

        val item0 = PrimaryDrawerItem().apply {
            nameRes = R.string.app_name; iconDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_buddha); isSelectable =
            false; identifier = 0
        }
        val item1 = PrimaryDrawerItem().apply {
            nameRes = R.string.fragment_quote; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_format_quote; isSelectable = false; identifier = 1
        }
        val item2 = PrimaryDrawerItem().apply {
            nameRes = R.string.fragment_lists; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_list; isSelectable = false; identifier = 2
        }
        val item3 = PrimaryDrawerItem().apply {
            nameRes = R.string.fragment_meditation; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_self_improvement; isSelectable = false; identifier = 3
        }
        val item4 = PrimaryDrawerItem().apply {
            nameRes = R.string.settings; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_settings; isSelectable = false; identifier = 4
        }
        val item5 = PrimaryDrawerItem().apply {
            nameRes = R.string.about; descriptionRes = R.string.view_app_details; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_info; isSelectable = false; identifier = 5
        }
        val item6 = PrimaryDrawerItem().apply {
            nameRes = R.string.open_source_libraries; descriptionRes =
            R.string.libraries_used; iconicsIcon =
            RoundedGoogleMaterial.Icon.gmr_library_books; isSelectable = false; identifier = 6
        }

        binding.slider.itemAdapter.add(
            item0,
            DividerDrawerItem(),
            item1,
            item2,
            item3,
            item4,
            item5,
            item6
        )

        var intent: Intent? = null
        binding.slider.onDrawerItemClickListener = { _, drawerItem, _ ->
            when (drawerItem.identifier) {
                0L -> intent = Intent(this, Settings::class.java).putExtra("from", Activities.MAIN)
                1L -> binding.bottomBar.selectTabAt(0, true)
                2L -> binding.bottomBar.selectTabAt(1, true)
                3L -> binding.bottomBar.selectTabAt(2, true)
                4L -> intent = Intent(this, Settings::class.java).putExtra("from", Activities.MAIN)
                5L -> intent = Intent(this, About::class.java)
                6L -> intent = Intent(this, AboutLibraries::class.java)
                else -> binding.slider.drawerLayout?.closeDrawer(binding.slider)
            }
            if (intent != null) {
                startActivity(intent)
            }
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

    /**
     * On options menu created
     * @param menu [Menu]
     * @return [Boolean]
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                showCreateListSheet()
                true
            }
            R.id.settings -> {
                val intent = Intent(this, Settings::class.java)
                intent.putExtra("from", Activities.MAIN)
                startActivity(intent)
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

        InputSheet().show(this) {
            title(R.string.createNewList)
            with(
                InputEditText {
                    required()
                    hint(R.string.insertName)
                    validationListener { value ->
                        when {
                            value.contains("//") -> {
                                Validation.failed(getString(R.string.validationRule1))
                            }
                            value.toLowerCase(Locale.ROOT) == "favourites" -> {
                                Validation.failed(getString(R.string.validationRule2))
                            }
                            lists.contains(value.toLowerCase(Locale.ROOT)) -> {
                                Validation.failed(getString(R.string.validationRule3) + " $value")
                            }
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }
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

    override fun onResume() {
        super.onResume()
        Colours().setAccentColour(this, window, resources)
        binding.toolbar.background = ContextCompat.getDrawable(this, R.drawable.toolbar)
        binding.bottomBar.tabColorSelected = Colours().getAccentColourAsInt(this)
        binding.bottomBar.indicatorColor = Colours().getAccentColourAsInt(this)
    }

    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (binding.root.isDrawerOpen(binding.slider)) {
            binding.root.closeDrawer(binding.slider)
        } else {
            super.onBackPressed()
        }
    }
}
