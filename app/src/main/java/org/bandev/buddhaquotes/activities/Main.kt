package org.bandev.buddhaquotes.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.maxkeppeler.bottomsheets.input.InputSheet
import com.maxkeppeler.bottomsheets.input.Validation
import com.maxkeppeler.bottomsheets.input.type.InputEditText
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.MainActivityBinding
import org.bandev.buddhaquotes.fragments.FragmentAdapter
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

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourMain(this, window, resources)
        Languages().setLanguage(this)

        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()

        if (sharedPreferences.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            val i = Intent(this, Intro::class.java)
            startActivity(i)
        } else if (sharedPreferences.getString(
                "latestShown",
                "null"
            ) != getString(R.string.version)
        ) {
            startActivity(Intent(this, UpdateInfo::class.java))
        }

        // Setup view binding
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)

        // Setup viewPager with FragmentAdapter
        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.setCurrentItem(Store(this).fragment, false)
        binding.bottomBar.setupWithViewPager2(binding.viewPager)
        val sharedPreferences2 = getSharedPreferences("timer", 0)

        if (sharedPreferences2.getBoolean("new", true)) {
            binding.bottomBar.setBadgeAtTabIndex(2, AnimatedBottomBar.Badge("New"))
        }


        binding.bottomBar.setOnTabInterceptListener(object :
            AnimatedBottomBar.OnTabInterceptListener {
            override fun onTabIntercepted(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ): Boolean {
                Store(applicationContext).fragment = newIndex
                return true
            }
        })
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

    /**
     * On options menu item selected
     * @param item [MenuItem]
     * @return [Boolean]
     */

    // Build the input bottom sheet that allows creation of a new list
    val addToListSheet: InputSheet = InputSheet().build(this) {
        title("Create new list")
        with(InputEditText {
            required()
            hint("Insert name")
            validationListener { value ->
                when {
                    value.contains("//") -> {
                        Validation.failed("Cannot contain //")
                    }
                    value.toLowerCase(Locale.ROOT) == "favourites" -> {
                        Validation.failed("There is already a list named Favourites")
                    }
                    Lists().getMasterList(requireContext())
                        .contains(value.toLowerCase(Locale.ROOT)) -> {
                        Validation.failed("There is already an list named $value")
                    }
                    else -> {
                        Validation.success()
                    }
                }
            }
            resultListener { value ->
                Lists().newList(value.toString(), requireContext())
            }
        })
        onNegative { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
        onPositive("Add") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }

            //Refresh fragments
            binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
            binding.viewPager.setCurrentItem(1, false)
            binding.bottomBar.setupWithViewPager2(binding.viewPager)
        }
    }
}