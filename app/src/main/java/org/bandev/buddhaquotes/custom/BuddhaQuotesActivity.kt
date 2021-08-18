package org.bandev.buddhaquotes.custom

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.navigation.NavigationView
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.core.TopStyle
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.LottieAnimationRequest
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.AboutActivity
import org.bandev.buddhaquotes.activities.SettingsActivity
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.core.Accent.setAccentColour
import org.bandev.buddhaquotes.core.Bars.updateNavbarColour
import org.bandev.buddhaquotes.core.InsetType
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.core.setDarkStatusIcons

abstract class BuddhaQuotesActivity<VB : ViewBinding> : LocalizationActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    /** View as provided by activity */
    private lateinit var view: ViewBinding

    /** ViewModel as provided by activity */
    var viewModel: ViewModel? = null

    /** The binding inflater */
    abstract val bInflater: (LayoutInflater) -> VB

    var toolbar: Toolbar? = null

    /**
     * Code to setup the activity goes here. Remember
     * binding has already been setup and is ready to
     * use with [binding].
     */
    abstract fun create()

    abstract fun resume()

    /** Provide access to binding */
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = view as VB

    /** Assign a ViewModel to the activity */
    fun assignViewModel(_viewModel: Class<out ViewModel>): ViewModel {
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(_viewModel)
        return viewModel as ViewModel
    }

    /** When the activity is started */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = bInflater.invoke(layoutInflater)
        setContentView(view.root)
        window.apply {
            statusBarColor = Color.TRANSPARENT
            updateNavbarColour()
            setDarkStatusIcons()
            WindowCompat.setDecorFitsSystemWindows(this, false)
        }
        create()
    }

    fun setNavBarColour(@ColorRes res: Int) {
        window.navigationBarColor = ContextCompat.getColor(this, res)
    }

    fun assignToolbar(t: Toolbar) {
        toolbar = t
        setSupportActionBar(toolbar)
        toolbar!!.applyInsets(InsetType.STATUS_BARS)
    }

    fun setupNavigationView(dl: DrawerLayout, nv: NavigationView, t: Toolbar? = toolbar, mr: (MenuItem) -> Any): ActionBarDrawerToggle {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            dl,
            t,
            R.string.open_drawer,
            R.string.close_drawer
        )
        dl.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        t!!.setNavigationOnClickListener { if (dl.isOpen) dl.close() else dl.open() }
        nv.apply {
            setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                mr(menuItem)
                dl.close()
                true
            }
        }
        return actionBarDrawerToggle
    }

    fun open(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }


    open fun menuItemSelected(item: MenuItem) {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItemSelected(item)
        return true
    }

    /** When the activity is resumed */
    override fun onResume() {
        super.onResume()
        setAccentColour()
        resume()
    }

}