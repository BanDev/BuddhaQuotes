@file:Suppress("ClassName")

package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mikepenz.aboutlibraries.LibsBuilder

class Settings : AppCompatActivity() {

    private var quotenumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        quotenumber = ((intent.extras ?: return).getString("quote") ?: return).toInt()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        window.statusBarColor = ContextCompat.getColor(this@Settings, R.color.colorTop)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.arrow_back_white_18dp)
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
            } // Night mode is active, we're using dark theme
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
                    val i = Intent(activity, Slide1::class.java)
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
            } else if (!dark) {
                listPreference?.setValueIndex(0)
            }

            (listPreference ?: return).onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    listPreference.value = newValue.toString()
                    val theme = listPreference.entry.toString()
                    Log.d("debug", theme)
                    if (theme == "Light") {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        editor.putBoolean("dark_mode", false)
                        editor.putBoolean("sys", false)
                        editor.apply()
                    }
                    if (theme == "Dark") {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        editor.putBoolean("dark_mode", true)
                        editor.putBoolean("sys", false)
                        editor.commit()
                    }
                    if (theme == "Follow system default") {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        editor.putBoolean("dark_mode", false)
                        editor.putBoolean("sys", true)
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
                    if (size == "Extra") {
                        editor.putString("text_size", "xlg")
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
