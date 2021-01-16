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
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import org.bandev.buddhaquotes.R

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
            Configuration.UI_MODE_NIGHT_YES -> {
                setStatusBarColorRes(R.color.background)
                setNavBarColorRes(R.color.background)
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
        }
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
