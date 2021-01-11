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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.databinding.UpdateInfoBinding

/**
 * The activity that shows the user a little message about the new update.
 * Make sure you update the contents of this for each update !!!!!!!!!!!!!
 */

class UpdateInfo : AppCompatActivity() {

    private var updateTag = ""
    private var updateBlogLink = ""
    private lateinit var binding: UpdateInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change these each update
        updateTag = getString(R.string.version)
        updateBlogLink =
            "https://medium.com/bandev/re-writing-to-make-it-better-buddha-quotes-v2-0-0-499eda297c6b?source=friends_link&sk=f5be0ec80ddf66c058c9a55883c9f29c"

        // Inflate binding for update_info.xml
        binding = UpdateInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle button clicks
        binding.carryOn.setOnClickListener { finish() }
        binding.readDocs.setOnClickListener { readDocs() }

        // Mark this as shown in shared preferences so it doesn't get shown again
        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()
        editor.putString("latestShown", updateTag)
        editor.apply()
    }

    fun readDocs() {
        // Open the url for our changelog for the update
        val docs = Intent(Intent.ACTION_VIEW, Uri.parse(updateBlogLink))
        startActivity(docs)
    }
}
