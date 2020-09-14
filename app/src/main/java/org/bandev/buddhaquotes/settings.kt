package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class settings : AppCompatActivity() {

    var Quote_Number:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        Quote_Number = getIntent().getExtras()!!.getString("quote")!!.toInt()

        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }


    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            when (preference?.key) {
                "about" -> {
                    val i = Intent(activity, about::class.java)
                    startActivity(i)
                }
                "license" -> {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://gitlab.com/bandev/buddha-quotes/-/blob/master/LICENSE"))
                    startActivity(i)
                }
                "oss_libraries" -> {
                    val i = Intent(activity, oss_libraries::class.java)
                    val b = Bundle()
                    b.putString("from", "settings") //Your id
                    i.putExtras(b)
                    startActivity(i)
                }
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
                listPreference.setValueIndex(2) //set to index of your default value
            }
            listPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
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
        val myIntent = Intent(this@settings, MainActivity::class.java)

        val mBundle = Bundle()
        mBundle.putString("quote", Quote_Number.toString())
        myIntent.putExtras(mBundle)
        this@settings.startActivity(myIntent)
        overridePendingTransition(R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@settings, MainActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("quote", Quote_Number.toString())
        myIntent.putExtras(mBundle)
        this@settings.startActivity(myIntent)

        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, MainActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", Quote_Number.toString())
                i.putExtras(mBundle)
                startActivity(i)
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
