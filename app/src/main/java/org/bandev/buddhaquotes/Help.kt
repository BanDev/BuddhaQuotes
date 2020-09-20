package org.bandev.buddhaquotes

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class Help : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!
        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0
            )
        )

        setStatusBarColor(getColor(R.color.colorAccent))
        setNavBarColorRes(R.color.colorAccent)

        isWizardMode = true

        isVibrate = true
        vibrateDuration = 50L
        showStatusBar(true)

        isIndicatorEnabled = true

        // Change Indicator Color
        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.colorPrimary),
            unselectedIndicatorColor = getColor(R.color.appintro_ripple_color)
        )
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
                title = "Welcome...",
                description = "This is the first slide of the example",
                backgroundColor = getColor(R.color.colorAccent),
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "...Let's get started!",
                description = "This is the last slide, I won't annoy you more :)"
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


