package org.bandev.buddhaquotes.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bandev.buddhaquotes.core.Lists
import org.bandev.buddhaquotes.core.ListsV2
import org.bandev.buddhaquotes.core.Quotes
import org.bandev.buddhaquotes.databinding.ActivityMigrateBinding

class Migrate : AppCompatActivity() {

    private lateinit var binding: ActivityMigrateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setup view
        binding = ActivityMigrateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yes.setOnClickListener { begin() }

        val sharedPrefs = getSharedPreferences("Settings", 0)
        val editor = sharedPrefs.edit()

        if (sharedPrefs.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            startActivity(Intent(this, Intro::class.java))
            finish()
        }

        if (!sharedPrefs.getBoolean("old_quotes", true)) {
            startActivity(Intent(this, Main::class.java))
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
        startActivity(Intent(this, Main::class.java))
        finish()
    }


}