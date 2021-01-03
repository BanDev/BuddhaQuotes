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