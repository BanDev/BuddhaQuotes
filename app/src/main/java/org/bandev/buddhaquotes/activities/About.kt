package org.bandev.buddhaquotes.activities

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityAboutBinding

class About : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)

        //Setup view binding & force portrait mode
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val contributorsList: ListView = findViewById(R.id.contributorsList)
        val contributors = this.getStringArray(R.array.contributors)
        val contributorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, contributors)
        contributorsList.adapter = contributorsAdapter
        contributorsList.divider = null
        contributorsList.isClickable = false
        justifyListViewHeightBasedOnChildren(contributorsList)

        val translatorsList: ListView = findViewById(R.id.translatorsList)
        val translators = this.getStringArray(R.array.translators)
        val translatorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, translators)
        translatorsList.adapter = translatorsAdapter
        translatorsList.divider = null
        translatorsList.isClickable = false
        justifyListViewHeightBasedOnChildren(translatorsList)

        var done = false

        binding.scrollView.viewTreeObserver
            .addOnScrollChangedListener {
                if (binding.scrollView.getChildAt(0).bottom <= binding.scrollView.height + binding.scrollView.scrollY && !done) {
                    binding.viewKonfetti.build()
                        .addColors(
                            Color.parseColor("#A864FD"),
                            Color.parseColor("#29CDFF"),
                            Color.parseColor("#78FF44"),
                            Color.parseColor("#FF718D"),
                            Color.parseColor("#FDFF6A")
                        )
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(
                            Shape.Square,
                            Shape.Circle
                        )
                        .addSizes(Size(10))
                        .setPosition(-50f, binding.viewKonfetti.width + 50f, -50f, -50f)
                        .streamFor(100, 1000L)
                    done = true

                    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                200,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(200)
                    }
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun justifyListViewHeightBasedOnChildren(listView: ListView) {
        val adapter = listView.adapter ?: return
        val vg: ViewGroup = listView
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem: View = adapter.getView(i, null, vg)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight + 10
        }
        val par = listView.layoutParams
        par.height = totalHeight + listView.dividerHeight * (adapter.count - 1)
        listView.layoutParams = par
        listView.requestLayout()
    }
}
