package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mikepenz.aboutlibraries.LibsBuilder
import org.bandev.buddhaquotes.slides.S1Intro

class Settings : AppCompatActivity() {

    private var quotenumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)




        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.black, null)
        } else {
            window.navigationBarColor =
                ResourcesCompat.getColor(resources, R.color.transparent, null)
        }

        if ((intent.extras ?: return).getBoolean("switch")) {
            val sharedPreferences = getSharedPreferences("Settings", 0)
            val darkmode = sharedPreferences.getBoolean("dark_mode", false)
            val sys = sharedPreferences.getBoolean("sys", true)
            when {
                sys -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
                }
                darkmode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
                }
            }
        }



        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)

        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        window.statusBarColor = ContextCompat.getColor(this@Settings, R.color.colorTop)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = (myToolbar ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        myToolbar.layoutParams = param

        val view = View(this)


        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            when (preference?.key) {
                "About" -> {
                    val i = Intent(activity, About::class.java)
                    startActivity(i)
                }
                "license" -> {
                    val i = Intent(activity, License::class.java)
                    startActivity(i)
                }
                "oss_libraries" -> {
                    context?.let {
                        LibsBuilder()
                            .withAboutIconShown(true)
                            .withAboutVersionShown(false)
                            .start(it)
                    } // start the activity
                }
                "help" -> {
                    val i = Intent(activity, S1Intro::class.java)
                    startActivity(i)
                }
            }
            return true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val pref = requireContext().getSharedPreferences("Settings", 0)
            val editor = pref.edit()

            val dark = pref.getBoolean("dark_mode", false)
            val sys = pref.getBoolean("sys", false)

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val screen = preferenceScreen

            val listPreference = findPreference<Preference>("theme") as ListPreference?

            if (sys) {
                listPreference?.setValueIndex(2)
            } else if (dark) {
                listPreference?.setValueIndex(1) // Set to index of your default value
                listPreference?.setIcon(R.drawable.night_settings)
            } else if (!dark) {
                listPreference?.setValueIndex(0)
                listPreference?.setIcon(R.drawable.day_settings)
            }


            (listPreference ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    listPreference.value = newValue.toString()
                    val theme = listPreference.entry.toString()
                    Log.d("debug", theme)
                    if (theme == "Light") {
                        editor.putBoolean("dark_mode", false)
                        editor.putBoolean("sys", false)
                        editor.apply()

                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("switch", true)
                        intent2.putExtras(mBundle)

                        startActivity(intent2)
                        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    if (theme == "Dark") {
                        editor.putBoolean("dark_mode", true)
                        editor.putBoolean("sys", false)
                        editor.commit()

                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("switch", true)
                        intent2.putExtras(mBundle)

                        startActivity(intent2)

                        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    if (theme == "Follow system default") {
                        editor.putBoolean("dark_mode", false)
                        editor.putBoolean("sys", true)
                        editor.commit()

                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("switch", true)
                        intent2.putExtras(mBundle)


                        startActivity(intent2)

                        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    true
                }

            val funMode = findPreference<Preference>("fun") as ListPreference?
            (funMode ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    funMode.value = newValue.toString()
                    val areTheyCool = funMode.entry.toString()
                    Log.d("debug", areTheyCool)
                    if (areTheyCool == "Yes, I like The Cool Shapes :)") {
                        editor.putBoolean("fun_mode", true)
                        editor.apply()
                    }
                    if (areTheyCool == "Think Of The Overdraw!!! :(") {
                        editor.putBoolean("fun_mode", false)
                        editor.commit()
                    }
                    true
                }

            val textSize = findPreference<Preference>("size") as ListPreference?

            when (pref.getString("text_size", "md")) {
                "md" -> {
                    textSize?.setValueIndex(1) // Set to index of your default value
                }
                "sm" -> {
                    textSize?.setValueIndex(0)
                }
                "lg" -> {
                    textSize?.setValueIndex(2)
                }
            }

            textSize?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    textSize?.value = newValue.toString()
                    val size = textSize?.entry.toString()
                    Log.d("debug", size)
                    if (size == "Small") {
                        editor.putString("text_size", "sm")
                        editor.commit()
                    }
                    if (size == "Medium") {
                        editor.putString("text_size", "md")
                        editor.commit()
                    }
                    if (size == "Large") {
                        editor.putString("text_size", "lg")
                        editor.commit()
                    }
                    true
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@Settings, MainActivity::class.java)

        val mBundle = Bundle()
        mBundle.putString("quote", quotenumber.toString())
        myIntent.putExtras(mBundle)
        this@Settings.startActivity(myIntent)
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@Settings, MainActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("quote", quotenumber.toString())
        myIntent.putExtras(mBundle)
        this@Settings.startActivity(myIntent)

        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, MainActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", quotenumber.toString())
                i.putExtras(mBundle)
                startActivity(i)
                overridePendingTransition(
                    R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right
                )
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
