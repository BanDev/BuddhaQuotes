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

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowInsetsController
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import org.bandev.buddhaquotes.R

/**
 * The Intro activity is the first thing people see on buddha quotes
 * AppIntro is used to make it more simple
 */
class Intro : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isVibrate = true
        vibrateDuration = 30L

        // If this is run, user is new so mark latestShown as shown for this
        // release meaning they all of the new release rubbish until they
        // update!
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()
        editor.putString("latestShown", getString(R.string.version))
        editor.apply()

        showStatusBar(true)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                setupNavBarForLightMode()
                setupStatusBarForLightMode()
                lightModeSlides()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                darkModeSlides()
            }
        }
    }

    private fun lightModeSlides() {
        setIndicatorColor(
            selectedIndicatorColor = Color.BLACK,
            unselectedIndicatorColor = Color.GRAY
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.app_name),
                titleColor = Color.BLACK,
                description = getString(R.string.appintro1_description),
                descriptionColor = Color.BLACK,
                imageDrawable = R.drawable.ic_buddha_no_background,
                backgroundColor = Color.WHITE,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro2_title),
                titleColor = Color.BLACK,
                description = getString(R.string.appintro2_description),
                descriptionColor = Color.BLACK,
                imageDrawable = R.drawable.ic_quotation_marks,
                backgroundColor = Color.WHITE,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro3_title),
                titleColor = Color.BLACK,
                description = getString(R.string.appintro3_description),
                descriptionColor = Color.BLACK,
                imageDrawable = R.drawable.heart_full_white_large,
                backgroundColor = Color.WHITE,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro4_title),
                titleColor = Color.BLACK,
                description = getString(R.string.appintro4_description),
                descriptionColor = Color.BLACK,
                imageDrawable = R.drawable.ic_palette_large,
                backgroundColor = Color.WHITE,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro5_title),
                titleColor = Color.BLACK,
                description = getString(R.string.appintro5_description),
                descriptionColor = Color.BLACK,
                imageDrawable = R.drawable.ic_meditate,
                backgroundColor = Color.WHITE,
            )
        )
    }

    private fun darkModeSlides() {
        setStatusBarColorRes(R.color.background)
        setNavBarColorRes(R.color.background)
        setIndicatorColor(
            selectedIndicatorColor = Color.WHITE,
            unselectedIndicatorColor = Color.GRAY
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.app_name),
                description = getString(R.string.appintro1_description),
                imageDrawable = R.drawable.ic_buddha_no_background,
                backgroundDrawable = R.color.background,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro2_title),
                description = getString(R.string.appintro2_description),
                imageDrawable = R.drawable.ic_quotation_marks,
                backgroundDrawable = R.color.background,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro3_title),
                description = getString(R.string.appintro3_description),
                imageDrawable = R.drawable.heart_full_white_large,
                backgroundDrawable = R.color.background,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro4_title),
                description = getString(R.string.appintro4_description),
                imageDrawable = R.drawable.ic_palette_large,
                backgroundDrawable = R.color.background,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.appintro5_title),
                description = getString(R.string.appintro5_description),
                imageDrawable = R.drawable.ic_meditate,
                backgroundDrawable = R.color.background,
            )
        )
    }

    private fun setupNavBarForLightMode() {
        if (!ViewConfiguration.get(this).hasPermanentMenuKey()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
        setNavBarColor(Color.WHITE)
    }

    private fun setupStatusBarForLightMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS, // value
                APPEARANCE_LIGHT_STATUS_BARS // mask
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setStatusBarColor(Color.WHITE)
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
