package org.bandev.buddhaquotes

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

class settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, settings.SettingsFragment())
                .commit()
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val darkmode = sharedPreferences.getBoolean("dark_mode", false)
        if (darkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val actionBar = supportActionBar
        assert(supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }


    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            val key = preference?.key
            if (key == "about") {
                val i = Intent(getActivity(), about::class.java)
                startActivity(i)
            } else if(key == "license"){
                val i = Intent(getActivity(), licenses::class.java)
                val b = Bundle()
                b.putString("from", "settings") //Your id
                i.putExtras(b)
                startActivity(i)
            } else if(key == "oss_libraries"){
                val i = Intent(getActivity(), oss_libraries::class.java)
                val b = Bundle()
                b.putString("from", "settings") //Your id
                i.putExtras(b)
                startActivity(i)
            }
            return true
        }
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val pref = requireContext().getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val screen = preferenceScreen
            val listPreference = findPreference<Preference>("theme") as ListPreference?
            if (listPreference!!.value == null) {
                listPreference.setValueIndex(0) //set to index of your deafult value
            }
            listPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                listPreference.value = newValue.toString()
                val theme = listPreference.entry.toString()
                Log.d("debug", theme)
                if (theme == "Light") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("dark_mode", false)
                    editor.commit()
                }
                if (theme == "Dark") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("dark_mode", true)
                    editor.commit()
                }
                true
            }
            val textSize = findPreference<Preference>("size") as ListPreference?
            if (textSize!!.value == null) {
                textSize.setValueIndex(1) //set to index of your default value
            }
            textSize.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                textSize.value = newValue.toString()
                val size = textSize.entry.toString()
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
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
