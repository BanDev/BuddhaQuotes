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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.AddQuoteRecycler
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.AddlistContentBinding
import org.bandev.buddhaquotes.items.AddQuoteItem
import java.util.*


/**
 * The activity where the user selects a quote to add to their list
 * If calling, make sure to send the name of the list they want with
 * the key "list" in the intent
 **/
class AddToList : AppCompatActivity(), AddQuoteRecycler.ClickListener {

    private lateinit var binding: AddlistContentBinding
    private lateinit var rAdapter: AddQuoteRecycler
    private lateinit var mToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = AddlistContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        rAdapter = AddQuoteRecycler(genList(), this@AddToList)

        with(binding.recycler) {
            adapter = rAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        mToolbar = binding.toolbar
    }

    override fun onClick(quote: String) {
        val list = (intent.extras ?: return).getString("list").toString()
        if (!Lists().queryInList(quote, list, this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
            Lists().addToList(quote, list, this)
            val intent2 = Intent(this, ScrollingActivity::class.java)
            intent2.putExtra("list", list)
            startActivity(intent2)
            finish()
            overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.REJECT)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
            Snackbar.make(binding.root, "This quote is already in $list", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun genList(): ArrayList<AddQuoteItem> {
        val list = ArrayList<AddQuoteItem>()
        val max = 237
        var i = 1
        while (i != max) {
            val quote = Quotes().getQuote(i, this)
            list.add(AddQuoteItem(quote))
            i++
        }

        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchItem = menu!!.findItem(R.id.appSearchBar)
        val searchView = searchItem.actionView as android.widget.SearchView
        searchView.isActivated = true
        val searchMagIcon = resources.getIdentifier("search_mag_icon", "id", "android")
        val SearchHintIcon: ImageView = searchView.findViewById<View>(searchMagIcon) as ImageView
        SearchHintIcon.setImageResource(0)
        val id = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<View>(id) as TextView
        textView.setTextColor(Color.BLACK)
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        MenuItemCompat.setOnActionExpandListener(
            searchItem,
            object : MenuItemCompat.OnActionExpandListener {
                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    // Called when SearchView is collapsing
                    if (searchItem.isActionViewExpanded()) {
                        animateSearchToolbar(1, false, false)
                    }
                    return true
                }

                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    // Called when SearchView is expanding
                    animateSearchToolbar(1, true, true)
                    return true
                }
            })
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.md_blue_grey_100));
        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = mToolbar.getWidth() -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal = ViewAnimationUtils.createCircularReveal(
                    mToolbar,
                    if (isRtl(resources)) mToolbar.getWidth() - width else width,
                    mToolbar.getHeight() / 2,
                    0.0f,
                    width.toFloat()
                )
                createCircularReveal.duration = 250
                createCircularReveal.start()
            } else {
                val translateAnimation =
                    TranslateAnimation(0.0f, 0.0f, -mToolbar.getHeight() as Float, 0.0f)
                translateAnimation.duration = 220
                mToolbar.clearAnimation()
                mToolbar.startAnimation(translateAnimation)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = mToolbar.getWidth() -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal = ViewAnimationUtils.createCircularReveal(
                    mToolbar,
                    if (isRtl(resources)) mToolbar.getWidth() - width else width,
                    mToolbar.getHeight() / 2,
                    width.toFloat(),
                    0.0f
                )
                createCircularReveal.duration = 250
                createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        mToolbar.setBackgroundColor(
                            getThemeColor(
                                this@AddToList,
                                R.attr.colorPrimary
                            )
                        )
                        getWindow().setStatusBarColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.md_blue_grey_100
                            )
                        );

                    }
                })
                createCircularReveal.start()
            } else {
                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                val translateAnimation: Animation =
                    TranslateAnimation(0.0f, 0.0f, 0.0f, -mToolbar.getHeight() as Float)
                val animationSet = AnimationSet(true)
                animationSet.addAnimation(alphaAnimation)
                animationSet.addAnimation(translateAnimation)
                animationSet.duration = 220
                animationSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        mToolbar.setBackgroundColor(
                            getThemeColor(
                                this@AddToList,
                                R.attr.colorPrimary
                            )
                        )
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                mToolbar.startAnimation(animationSet)
            }
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.md_blue_grey_100));

        }
    }

    private fun isRtl(resources: Resources): Boolean {
        return resources.getConfiguration().getLayoutDirection() === View.LAYOUT_DIRECTION_RTL
    }

    private fun getThemeColor(context: Context, id: Int): Int {
        val theme: Resources.Theme = context.getTheme()
        val a: TypedArray = theme.obtainStyledAttributes(intArrayOf(id))
        val result = a.getColor(0, 0)
        a.recycle()
        return result
    }

    override fun onBackPressed() {
        val list = (intent.getStringExtra("list") ?: return).toString()
        val intent2 = Intent(this, ScrollingActivity::class.java)
        intent2.putExtra("list", list)
        startActivity(intent2)
        finish()
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
    }
}
