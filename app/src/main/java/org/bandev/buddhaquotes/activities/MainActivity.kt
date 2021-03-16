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
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.iconDrawable
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.widget.AccountHeaderView
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

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

        // Set navigation bar and language
        Compatibility().setNavigationBarColourMain(this, window)
        Languages(baseContext).setLanguage()

        val sharedPrefs = getSharedPreferences("Settings", 0)
        val editor = sharedPrefs.edit()
        if (sharedPrefs.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            startActivity(Intent(this, IntroActivity::class.java))
        }

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = IconicsDrawable(context, RoundedGoogleMaterial.Icon.gmr_menu).apply {
                colorInt = Color.WHITE
                sizeDp = 20
            }
            setBackgroundColor(Colours().toolbarColour(context))
            setNavigationOnClickListener {
                onBackPressed()
            }
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

        buildHeader(false, savedInstanceState)

        binding.slider.apply {
            itemAdapter.add(
                PrimaryDrawerItem().apply {
                    nameRes = R.string.app_name; iconDrawable =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_buddha
                    ); isSelectable =
                    false; identifier = 0
                },
                SectionDrawerItem().apply { nameRes = R.string.vibrate_second },
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
                    nameRes = R.string.about; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_info; isSelectable = false; identifier = 5
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.settings; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_settings; isSelectable = false; identifier = 4
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.open_source_libraries; iconicsIcon =
                    RoundedGoogleMaterial.Icon.gmr_library_books; isSelectable = false; identifier =
                    6
                })
            setSavedInstance(savedInstanceState)
        }

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
        menu?.findItem(R.id.settings)?.icon =
            IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_settings).apply {
                colorInt = Color.WHITE
                sizeDp = 20
            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                showCreateListSheet()
                true
            }
            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java).putExtra("from", Activities.MAIN))
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }
        }
    }

    override fun onResume() {
        // Set the colour of everything to the accent colour when the user returns to the Main Activity
        super.onResume()
        Colours().setAccentColour(this)
        Colours().setStatusBar(this, window)
        binding.toolbar.setBackgroundColor(Colours().toolbarColour(this))
        with(binding.bottomBar) {
            tabColorSelected = Colours().getAccentColourAsInt(context)
            indicatorColor = Colours().getAccentColourAsInt(context)
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
        if (binding.root.isDrawerOpen(binding.slider)) {
            binding.root.closeDrawer(binding.slider)
        } else super.onBackPressed()
    }
}