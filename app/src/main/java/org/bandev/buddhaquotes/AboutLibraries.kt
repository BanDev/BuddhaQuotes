package org.bandev.buddhaquotes

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import kotlinx.android.synthetic.main.activity_about_libraries.*
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages

class AboutLibraries : AppCompatActivity() {

    private val adapter = LibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        setContentView(R.layout.activity_about_libraries)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        adapter.setItems(Libs(this).libraries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

        private val itemList = mutableListOf<Library>()

        fun setItems(items: List<Library>) {
            itemList.clear()
            itemList.addAll(items)
            notifyDataSetChanged()
        }

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
            private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            private val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
            private val versionTextView: TextView = itemView.findViewById(R.id.versionTextView)
            private val licenseNameTextView: TextView =
                itemView.findViewById(R.id.licenseNameTextView)
            private val licenseDescriptionTextView: TextView =
                itemView.findViewById(R.id.licenseDescriptionTextView)

            @Suppress("DEPRECATION")
            fun onBind(library: Library) {

                nameTextView.text = library.libraryName
                versionTextView.text = library.libraryVersion

                if (library.author.isNotEmpty()) {
                    authorTextView.text = library.author
                } else {
                    authorTextView.visibility = View.GONE
                }

                val license = library.licenses?.firstOrNull()
                if (license != null) {
                    licenseNameTextView.text = license.licenseName
                    licenseDescriptionTextView.text =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(
                                license.licenseShortDescription,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        } else {
                            Html.fromHtml(license.licenseShortDescription)
                        }
                } else {
                    licenseNameTextView.visibility = View.GONE
                    licenseDescriptionTextView.visibility = View.GONE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}