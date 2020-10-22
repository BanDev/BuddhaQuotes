package org.bandev.buddhaquotes

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity(), ScrollingAdapter.OnItemClickFinder {

    private val scrollingList = generateDummyList(50)
    private val adapter = ScrollingAdapter(scrollingList, this)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val back = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_24)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        window.statusBarColor = ContextCompat.getColor(this@ScrollingActivity, R.color.colorTop)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Your List"

        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)


        if (intent.getBooleanExtra("safe", false)) {
            insertItem((intent.getStringExtra("quote") ?: return).toInt())
        }



        toolbar.navigationIcon = back

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }


        val view = View(this)
        view.doOnLayout {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
            // You can also access it from Window
            window.insetsController?.show(WindowInsets.Type.ime())
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
    }

    private fun insertItem(num: Int) {
        val index = 0
        val quote = Quotes()
        val text = quote.random(num)
        val newItem = ExampleItem(
            text,
            R.drawable.like,
        )

        scrollingList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_mode_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent2 = Intent(this, AddList::class.java)
                this.startActivity(intent2)
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this, ScrollingActivity::class.java)
                this.startActivity(intent2)
                overridePendingTransition(
                    R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right
                )
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLikeClick(position: Int) {
        val clickedItem = scrollingList[position]

        if (clickedItem.resource == R.drawable.heart_full_red) {
            clickedItem.resource = R.drawable.like
        } else {
            clickedItem.resource = R.drawable.heart_full_red
        }

        vibratePhone()
        adapter.notifyItemChanged(position)
    }

    override fun onShareClick(position: Int) {
        val clickedItem = scrollingList[position]
        vibratePhone()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND

            val text = clickedItem.quote + "\n\n~Buddha"

            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onBinClick(position: Int) {
        vibratePhone()

        scrollingList.removeAt(position)
        adapter.notifyItemRemoved(position)

    }

    private fun generateDummyList(size: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()
        val quote = Quotes()

        for (i in 0 until size) {
            val item = ExampleItem(quote.random(0), R.drawable.like)
            list += item
        }
        return list
    }

    private fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.EFFECT_HEAVY_CLICK))
        } else {
            vibrator.vibrate(200)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@ScrollingActivity, MainActivity::class.java)
        this@ScrollingActivity.startActivity(myIntent)
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@ScrollingActivity, MainActivity::class.java)
        val mBundle = Bundle()
        this@ScrollingActivity.startActivity(myIntent)
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )
        finish()
    }
}