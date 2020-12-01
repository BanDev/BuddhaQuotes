package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Splash : AppCompatActivity() {

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

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val i = Intent(this, MainActivity::class.java)
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }
}
