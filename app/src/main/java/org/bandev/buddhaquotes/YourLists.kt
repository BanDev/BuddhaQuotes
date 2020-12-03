package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.core.Lists
import org.bandev.buddhaquotes.databinding.ActivityYourListsBinding
import org.bandev.buddhaquotes.databinding.LayoutBottomSheetBinding
import java.util.*
import kotlin.collections.ArrayList

class YourLists : AppCompatActivity(), ScrollingAdapter.OnItemClickFinder {

    private lateinit var binding: ActivityYourListsBinding
    private lateinit var scrollingList: ArrayList<ListMenuItem>
    private lateinit var adapter: ListMenuAdapter
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String>

    override fun onLikeClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    override fun onShareClick(position: Int) {
        val intent2 = Intent(this, ScrollingActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("list", array[position])
        intent2.putExtras(mBundle)
        this.startActivity(intent2)
        finish()
    }

    override fun onBinClick(position: Int, text: String) {
        window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        Lists().removeList(text, this)
        scrollingList.removeAt(position)
        adapter.notifyItemRemoved(position)
        refresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBar(this, window, resources)
        Languages().setLanguage(this)
        binding = ActivityYourListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val toolbar = findViewById<View>(R.id.topAppBar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            val myIntent = Intent(this@YourLists, MainActivity::class.java)
            this@YourLists.startActivity(myIntent)
            finish()
        }

        val fab: FloatingActionButton = findViewById(R.id.bottomsheet)

        ViewCompat.setOnApplyWindowInsetsListener(fab) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom + 30
            insets.consumeSystemWindowInsets()
        }

        fab.setOnClickListener {
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            showBottomSheet()
        }

        refresh()
    }

    private fun showBottomSheet() {
        var nameValue = "error"
        val dialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            cornerRadius(16f)
            title(R.string.lists_add_lists)
            icon(R.drawable.ic_add_circle_bottomsheet)
            customView(R.layout.layout_bottom_sheet)
            positiveButton(R.string.lists_add_lists_go) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    (window ?: return@positiveButton).decorView.performHapticFeedback(
                        HapticFeedbackConstants.CONFIRM
                    )
                } else {
                    (window ?: return@positiveButton).decorView.performHapticFeedback(
                        HapticFeedbackConstants.VIRTUAL_KEY
                    )
                }

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                editor.putString(nameValue, "null")
                editor.putString(
                    "MASTER_LIST",
                    (pref.getString("MASTER_LIST", "Favourites") + "//" + nameValue)
                )

                editor.apply()
                refresh()
            }

            negativeButton(R.string.cancel) {
                (window ?: return@negativeButton).decorView.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY
                )
            }
        }

        dialog.getActionButton(WhichButton.POSITIVE).isEnabled = false
        val customView = dialog.getCustomView()
        val binding = LayoutBottomSheetBinding.bind(customView)
        val name = binding.nameEvent

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                when {
                    editable === name.text -> {
                        val input = name.text.toString()
                        nameValue = input
                        val pref = getSharedPreferences("List_system", 0)
                        val lists =
                            (pref.getString("MASTER_LIST", "Favourites")
                                ?: return).toLowerCase(
                                Locale.ROOT
                            )
                                .split("//".toRegex()).toTypedArray()
                        when {
                            input.isBlank() -> {
                                binding.nameEventLayout.error = "Cannot be blank"
                                dialog.getActionButton(WhichButton.POSITIVE).isEnabled =
                                    false
                            }
                            input.contains("//") -> {
                                binding.nameEventLayout.error = "Cannot contain //"
                                dialog.getActionButton(WhichButton.POSITIVE).isEnabled =
                                    false
                            }
                            lists.contains(input.toLowerCase(Locale.ROOT)) -> {
                                binding.nameEventLayout.error =
                                    "There is already a list named $input"
                                dialog.getActionButton(WhichButton.POSITIVE).isEnabled =
                                    false
                            }
                            else -> {
                                nameValue = input
                                binding.nameEventLayout.error = null
                                dialog.getActionButton(WhichButton.POSITIVE).isEnabled =
                                    true
                            }
                        }
                    }
                }
            }
        }

        name.addTextChangedListener(watcher)
    }

    fun refresh() {
        val pref = getSharedPreferences("List_system", 0)
        favs = arrayOf(pref.getString("MASTER_LIST", "Favourites"))
        array = (favs[0] ?: return).split("//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()

        scrollingList = generateDummyList(array.size)
        adapter = ListMenuAdapter(scrollingList, this)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ListMenuAdapter(scrollingList, this@YourLists)
            setHasFixedSize(false)
        }
    }

    private fun generateDummyList(max: Int): ArrayList<ListMenuItem> {

        val list = ArrayList<ListMenuItem>()

        var i = 0
        while (i != max) {
            var special = false
            val pref = getSharedPreferences("List_system", 0)
            val array2 = pref.getString(array[i], "")!!.split("//")
            val count: Int = array2.size - 1
            if (array[i] == "Favourites") {
                special = true
            }
            var summary = ""
            summary = if (count != 1) {
                "$count items"
            } else {
                "$count item"
            }
            val item = ListMenuItem(array[i], summary, special)
            list += item
            i++
        }
        return list
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@YourLists, MainActivity::class.java)
        this@YourLists.startActivity(myIntent)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myIntent = Intent(this@YourLists, MainActivity::class.java)
        val mBundle = Bundle()
        this@YourLists.startActivity(myIntent)
        finish()
    }
}