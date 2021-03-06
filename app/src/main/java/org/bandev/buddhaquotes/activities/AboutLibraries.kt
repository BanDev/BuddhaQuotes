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
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityAboutLibrariesBinding
import org.bandev.buddhaquotes.databinding.LayoutItemLibraryBinding

/**
 * Activity that shows all the libraries we use
 */
class AboutLibraries : AppCompatActivity() {

    private lateinit var binding: ActivityAboutLibrariesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivityAboutLibrariesBinding.inflate(layoutInflater)
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
            finish()
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = LibraryAdapter(Libs(context).libraries)
        }
    }

    class LibraryAdapter(private val itemList: List<Library>) :
        RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
            val rowView: View = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_library,
                parent, false
            )
            return LibraryViewHolder(rowView)
        }

        override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
            holder.onBind(itemList[position])
        }

        override fun getItemCount(): Int = itemList.size

        class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val binding = LayoutItemLibraryBinding.bind(itemView)

            fun onBind(library: Library) {

                binding.nameTextView.text = library.libraryName
                binding.versionTextView.text = library.libraryVersion

                if (library.author.isNotEmpty()) {
                    binding.authorTextView.text = library.author
                } else binding.authorTextView.visibility = View.GONE

                val license = library.licenses?.firstOrNull()
                if (license != null) {
                    binding.licenseNameTextView.text = license.licenseName
                    binding.licenseDescriptionTextView.text =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(
                                license.licenseShortDescription,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        } else {
                            @Suppress("DEPRECATION")
                            Html.fromHtml(license.licenseShortDescription)
                        }
                } else {
                    binding.licenseNameTextView.visibility = View.GONE
                    binding.licenseDescriptionTextView.visibility = View.GONE
                }
            }
        }
    }
}
