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

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityAboutBinding

/**
 * The about page
 */
class About : AppCompatActivity() {

    // Declare view binding variables
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        val returnDrawable =
            IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_arrow_back).apply {
                colorInt = Color.WHITE
                sizeDp = 16
            }
        setSupportActionBar(binding.toolbar)
        binding.toolbar.background = ContextCompat.getDrawable(this, R.drawable.toolbar)
        binding.toolbar.navigationIcon = returnDrawable
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Setup contributors array
        val contributors = resources.getStringArray(R.array.contributors)
        val contributorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, contributors)
        binding.contributorsList.adapter = contributorsAdapter
        binding.contributorsList.divider = null
        binding.contributorsList.isClickable = false
        justifyListViewHeightBasedOnChildren(binding.contributorsList)

        // Setup translators array
        val translators = resources.getStringArray(R.array.translators)
        val translatorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, translators)
        binding.translatorsList.adapter = translatorsAdapter
        binding.translatorsList.divider = null
        binding.translatorsList.isClickable = false
        justifyListViewHeightBasedOnChildren(binding.translatorsList)
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

    override fun onBackPressed() {
        finish()
    }
}
