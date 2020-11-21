package org.bandev.buddhaquotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class AppIntro : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        isVibrate = true
        vibrateDuration = 30L

        setImmersiveMode()

        setTransformer(AppIntroPageTransformerType.Fade)

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
                title = "Buddha Quotes",
                description = "Une application gratuite et open source sur les citations de Buddha",
                imageDrawable = R.drawable.ic_buddha_no_background,
                backgroundDrawable = R.drawable.slide_1,
                titleTypefaceFontRes = R.font.mt_med,
                descriptionTypefaceFontRes = R.font.mt_reg,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "Plus de 230 citations!",
                description = "Plus de 230 citations de Buddha",
                imageDrawable = R.drawable.ic_quotation_marks,
                backgroundDrawable = R.drawable.slide_2,
                titleTypefaceFontRes = R.font.mt_med,
                descriptionTypefaceFontRes = R.font.mt_reg,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "Favoris",
                description = "Vous pouvez ajouter vos citations en favories, en appuyant sur le bouton favori",
                imageDrawable = R.drawable.heart_full_white_large,
                backgroundDrawable = R.drawable.slide_3,
                titleTypefaceFontRes = R.font.mt_med,
                descriptionTypefaceFontRes = R.font.mt_reg,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "Customisation",
                description = "There is a variety of user interface options, such as accent colours and shapes mode",
                imageDrawable = R.drawable.ic_palette_large,
                backgroundDrawable = R.drawable.slide_4,
                titleTypefaceFontRes = R.font.mt_med,
                descriptionTypefaceFontRes = R.font.mt_reg,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "...C'est parti !",
                description = "Nous espérons que vous aimez l'application - nous avons un dépôt GitLab où vous pouvez demander des fonctionalités ou signaler des problèmes",
                imageDrawable = R.drawable.ic_meditate,
                backgroundDrawable = R.drawable.slide_5,
                titleTypefaceFontRes = R.font.mt_med,
                descriptionTypefaceFontRes = R.font.mt_reg,
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
