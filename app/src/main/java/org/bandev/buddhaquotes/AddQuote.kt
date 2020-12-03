package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.AddlistContentBinding
import java.util.*


class AddQuote : AppCompatActivity() {

    private lateinit var binding: AddlistContentBinding

    lateinit var toolbar: MaterialToolbar
    private lateinit var back: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBar(this, window, resources)
        Languages().setLanguage(this)
        binding = AddlistContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val list = (intent.getStringExtra("list") ?: return).toString()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val searchView = findViewById<SearchView>(R.id.searchView)
        val listView = findViewById<ListView>(R.id.listView)

        val names = genList()

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.quotes_search, names)

        listView.adapter = adapter

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString(list, "")
                val listArrFinal = LinkedList(listArr?.split("//"))
                val intent2 = Intent(this, ScrollingActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("list", list)
                intent2.putExtras(mBundle)
                if (listArrFinal.contains(listView.getItemAtPosition(position) as String?)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.REJECT)
                    } else {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    Snackbar.make(view, "This quote is already in the list!", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    listArrFinal.push(listView.getItemAtPosition(position) as String?)
                    this.startActivity(intent2)
                }

                val stringOut = listArrFinal.joinToString(separator = "//")

                editor.putString(list, stringOut)
                editor.apply()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
    }

    private fun genList(): ArrayList<String> {
        val list = ArrayList<String>()
        val max = 237
        var i = 1
        while (i != max) {
            list.add(Quotes().random(i, this))
            i++
        }

        return list
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
