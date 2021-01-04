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
        updateBlogLink = "https://medium.com/bandev"

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