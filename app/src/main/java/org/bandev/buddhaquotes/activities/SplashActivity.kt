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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.PreferenceManager

/**
 * The splash screen
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Meditation Timer"
            val descriptionText = "Meditation Timer Stuff"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("BQ.Timer", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        setDefaultNightMode(
            when (sharedPrefs.getInt("appThemeInt", 2)) {
                0 -> MODE_NIGHT_NO
                1 -> MODE_NIGHT_YES
                else -> MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
        startActivity(Intent(this, MigrateActivity::class.java))
        finish()
    }

}
