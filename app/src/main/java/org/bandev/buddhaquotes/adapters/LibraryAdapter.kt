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

package org.bandev.buddhaquotes.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.aboutlibraries.entity.Library
import org.bandev.buddhaquotes.core.AnimationUtils
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.databinding.LayoutItemLibraryBinding

class LibraryAdapter(private val list: List<Library>) :
    RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    class ViewHolder(binding: LayoutItemLibraryBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameTextView: TextView = binding.nameTextView
        val versionTextView: TextView = binding.versionTextView
        val authorTextView: TextView = binding.authorTextView
        val licenseNameTextView: TextView = binding.licenseNameTextView
        val licenseDescriptionTextView: TextView = binding.licenseDescriptionTextView
        val root: CardView = binding.root
        val cardExpandable: LinearLayout = binding.cardExpandable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutItemLibraryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val library = list[position]
        with(viewHolder) {
            nameTextView.text = library.libraryName
            versionTextView.text = library.libraryVersion
            authorTextView.text = library.author
            val license = library.licenses?.firstOrNull()
            licenseNameTextView.text = license?.licenseName
            licenseDescriptionTextView.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    license?.licenseShortDescription,
                    Html.FROM_HTML_MODE_COMPACT
                ) else Html.fromHtml(license?.licenseShortDescription)
            root.setOnClickListener {
                Feedback.virtualKey(it)
                val visible = cardExpandable.visibility != View.VISIBLE
                if (visible) AnimationUtils.expand(cardExpandable)
                else AnimationUtils.collapse(cardExpandable)
            }
        }
    }

    override fun getItemCount(): Int = list.size

}