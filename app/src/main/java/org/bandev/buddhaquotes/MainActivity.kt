package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.bandev.buddhaquotes.core.Activities
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.MainActivityBinding
import org.bandev.buddhaquotes.fragments.FragmentAdapter

/**
 * MainActivity is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 * @since v1.0.0
 * @author jack.txt & Fennec_exe
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    /**
     * On activity created
     *
     * @param savedInstanceState [Bundle]
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set theme, navigation bar and language
        Colours().setAccentColor(this, window, resources)
        Compatibility().setNavigationBar(this, window, resources)
        Languages().setLanguage(this)

        //Setup view binding & force portrait mode
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Setup toolbar
        setSupportActionBar(binding.toolbar)

        //Setup viewPager with FragmentAdapter
        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.bottomBar.setupWithViewPager2(binding.viewPager)

    }

    /**
     * On options menu created
     *
     * @param menu [Menu]
     * @return [Boolean]
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * On options menu item selected
     *
     * There is currently only 1 icon, there is no need to check which icon was pressed
     *
     * @param item [MenuItem]
     * @return [Boolean]
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, Settings::class.java)
        intent.putExtra("from", Activities.MAIN_ACTIVITY)
        this.startActivity(intent)
        return true
    }

}
