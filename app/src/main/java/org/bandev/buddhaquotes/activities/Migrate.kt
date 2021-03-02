package org.bandev.buddhaquotes.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.bandev.buddhaquotes.R
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
    }

    private fun begin() {
        val lists = Lists()
        val newLists = ListsV2(this)
        val quotes = Quotes()
        for (listTitle in lists.getMasterList(this)) {
            Toast.makeText(this, "Started translating $listTitle", Toast.LENGTH_SHORT).show()
            val list = lists.getList(listTitle, this)
            val newList = mutableListOf<Int>()
            for (quote in list) {
                if (quote == "") continue
                val id = quotes.getFromString(quote, this)
                Toast.makeText(this, "$quote -> $id", Toast.LENGTH_SHORT).show()
                newList.add(id)
            }
            Toast.makeText(this, "translating of $listTitle complete", Toast.LENGTH_SHORT).show()
            newLists.newList(listTitle, newList)
        }
    }


}