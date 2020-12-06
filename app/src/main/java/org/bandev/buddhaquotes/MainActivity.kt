package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

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

        //Set to draw behind system bars
        Compatibility().edgeToEdge(window, binding.root, binding.toolbar, resources)

        binding.root.setOnClickListener {
            val myIntent =
                Intent(this, Settings::class.java)
            val mBundle = Bundle()
            mBundle.putString("quote", 1.toString())
            myIntent.putExtras(mBundle)
            this.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of IDs because each menu should be considered as top level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}