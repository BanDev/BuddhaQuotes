package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mikepenz.aboutlibraries.LibsBuilder
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages

class Settings : AppCompatActivity() {

    private var quotenumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        setContentView(R.layout.activity_settings)

        if ((intent.extras ?: return).getBoolean("lang")) {
            this.overridePendingTransition(0, 0)
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.ic_arrow_back)

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
                "AboutLibraries" -> {
                    val i = Intent(activity, AboutLibraries::class.java)
                    startActivity(i)
                }
                "help" -> {
                    val i = Intent(activity, AppIntro::class.java)
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

            when {
                sys -> {
                    listPreference?.setValueIndex(2)
                }
                dark -> {
                    listPreference?.setValueIndex(1) // Set to index of your default value
                    listPreference?.setIcon(R.drawable.ic_night_settings)
                }
                else -> {
                    listPreference?.setValueIndex(0)
                    listPreference?.setIcon(R.drawable.ic_day_settings)
                }
            }

            val accentColorButton = findPreference<Preference>("accent_color") as ListPreference?

            (accentColorButton ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    listPreference!!.value = newValue.toString()
                    val intent2 = Intent(context, Settings::class.java)
                    val mBundle = Bundle()
                    mBundle.putBoolean("switch", true)
                    intent2.putExtras(mBundle)

                    startActivity(intent2)

                    true
                }

            val lang = findPreference<Preference>("app_language") as ListPreference?

            (lang ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    listPreference!!.value = newValue.toString()
                    val intent2 = Intent(context, Settings::class.java)
                    val mBundle = Bundle()
                    mBundle.putBoolean("lang", true)
                    intent2.putExtras(mBundle)

                    startActivity(intent2)

                    true
                }

            (listPreference ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    listPreference.value = newValue.toString()
                    val theme = listPreference.value.toString()
                    Log.d("debug", theme)
                    when (theme) {
                        "light" -> {
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
                        "dark" -> {
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
                        "sys" -> {
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
                    when (size) {
                        "Small" -> {
                            editor.putString("text_size", "sm")
                            editor.commit()
                        }
                        "Medium" -> {
                            editor.putString("text_size", "md")
                            editor.commit()
                        }
                        "Large" -> {
                            editor.putString("text_size", "lg")
                            editor.commit()
                        }
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
