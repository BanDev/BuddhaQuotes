package org.bandev.buddhaquotes

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import java.io.*

class License : AppCompatActivity() {

    private var stream: InputStream? = null

    //Say that you need a snazzy phone to use the app (Android Ver > R)
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        //The code for the weird slide in from left animation
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )

        //Setup toolbar which is the top bar thingy
        val mToolBar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolBar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        //Enable edge to edge in newer android versions (Hides top and bottom bars)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //Some magic to find the height of the devices status bar
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        //Now use the statusBarHeight to make sure you can see the toolbar where it should be
        val param = mToolBar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        mToolBar.layoutParams = param

        //Now tell android to show window insets
        val view = View(this)
        view.doOnLayout {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
            // You can also access it from Window
            window.insetsController?.show(WindowInsets.Type.ime())
        }

        //Ngl have no clue what this does (Oh ye its to pad the bottom so it doesn't appear under the nav bar)
        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        //Identify the TextView and then start loading the licenses into it
        val textview: TextView = findViewById(R.id.license)
        stream = File("Licenses/app.txt").inputStream()
        val bytes = ByteArray(32)
        loadFileIntoTextView((stream as FileInputStream).read(bytes), textview)
    }

    private fun loadFileIntoTextView(@RawRes fileId: Int, textView: TextView) {
        val sb = StringBuilder()
        val br = BufferedReader(InputStreamReader(resources.openRawResource(fileId)))

        try {
            var line: String? = br.readLine()
            while (line != null) {
                sb.append(line)
                sb.append('\n')
                line = br.readLine()
            }
        } catch (e: IOException) {
            Log.w("LicenseActivity", e)
        }

        stream?.close()
        textView.text = sb.toString()
    }
}