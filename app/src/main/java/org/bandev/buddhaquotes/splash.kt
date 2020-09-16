package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val darkmode = sharedPreferences.getBoolean("dark_mode", false)
        val sys = sharedPreferences.getBoolean("sys", true)
        when {
            sys -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            darkmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        val i = Intent(this, MainActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("quote", "0")
        i.putExtras(mBundle)
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }
}