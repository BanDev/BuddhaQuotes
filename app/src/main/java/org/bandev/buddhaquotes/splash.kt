package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val darkmode = sharedPreferences.getBoolean("dark_mode", false)
        val sys = sharedPreferences.getBoolean("sys", true)
        if (sys){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }else if (darkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val i = Intent(this, MainActivity::class.java)
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}