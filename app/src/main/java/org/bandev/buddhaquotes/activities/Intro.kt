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

import android.graphics.Color
import android.os.Bundle
import android.view.ViewConfiguration
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import org.bandev.buddhaquotes.R

class Intro : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        isVibrate = true
        vibrateDuration = 30L
        setTransformer(AppIntroPageTransformerType.Fade)

        // If this is run, user is new so mark latestShown as shown for this
        // release meaning they all of the new release rubbish until they
        // update!
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()
        editor.putString("latestShown", getString(R.string.version))
        editor.apply()

        // Checks whether the user has a navigation bar or uses gestures
        if (!ViewConfiguration.get(this).hasPermanentMenuKey()) {
            // If they have a navigation bar, hide the status bar and navigation bar
            setImmersiveMode()
        } else {
            // If they use gestures, show the status bar, set it transparent and draw the background behind it
            showStatusBar(true)
            setStatusBarColor(Color.TRANSPARENT)
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        // Call addSlide passing your Fragments
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.app_name),
                description = getString(R.string.appintro1_description),
                imageDrawable = R.drawable.ic_buddha_no_background,
                backgroundDrawable = R.drawable.slide_1,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro2_title),
                description = getString(R.string.appintro2_description),
                imageDrawable = R.drawable.ic_quotation_marks,
                backgroundDrawable = R.drawable.slide_2,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro3_title),
                description = getString(R.string.appintro3_description),
                imageDrawable = R.drawable.heart_full_white_large,
                backgroundDrawable = R.drawable.slide_3,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro4_title),
                description = getString(R.string.appintro4_description),
                imageDrawable = R.drawable.ic_palette_large,
                backgroundDrawable = R.drawable.slide_4,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro5_title),
                description = getString(R.string.appintro5_description),
                imageDrawable = R.drawable.ic_meditate,
                backgroundDrawable = R.drawable.slide_5,
            )
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }
}
