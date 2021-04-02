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
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bandev.buddhaquotes.core.Lists
import org.bandev.buddhaquotes.core.ListsV2
import org.bandev.buddhaquotes.core.Quotes
import org.bandev.buddhaquotes.databinding.ActivityMigrateBinding

class MigrateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMigrateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMigrateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yes.setOnClickListener { begin() }

        val sharedPrefs = getSharedPreferences("Settings", 0)

        if (!sharedPrefs.getBoolean("old_quotes", true)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun begin() {
        val lists = Lists()
        val newLists = ListsV2(this)
        val quotes = Quotes()
        for (listTitle in lists.getMasterList(this)) {
            val list = lists.getList(listTitle, this)
            val newList = mutableListOf<Int>()
            for (quote in list) {
                if (quote == "") continue
                val id = quotes.getFromString(quote, this)
                newList.add(id)
            }
            newList.add(-1)
            newLists.newList(listTitle, newList)
        }
        val sharedPrefs = getSharedPreferences("Settings", 0)
        val editor = sharedPrefs.edit()
        editor.putBoolean("old_quotes", false)
        editor.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
