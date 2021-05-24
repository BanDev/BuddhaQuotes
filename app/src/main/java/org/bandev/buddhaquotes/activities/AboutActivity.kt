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
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityAboutBinding

/**
 * The about page
 */
class AboutActivity : AppCompatActivity() {

    // Declare view binding variables
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup view binding
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set accent colour, navigation bar and language
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)
        Languages(baseContext).setLanguage()

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = context.backIcon()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        // Setup contributors array
        val contributors = resources.getStringArray(R.array.contributors)
        val contributorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, contributors)
        with(binding.contributorsList) {
            adapter = contributorsAdapter
            divider = null
            isClickable = false
        }

        // Setup translators array
        val translators = resources.getStringArray(R.array.translators)
        val translatorsAdapter = ArrayAdapter(this, R.layout.layout_list_item, translators)
        with(binding.translatorsList) {
            adapter = translatorsAdapter
            divider = null
            isClickable = false
        }
    }
}
