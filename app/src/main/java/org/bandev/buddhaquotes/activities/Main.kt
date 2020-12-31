package org.bandev.buddhaquotes.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.maxkeppeler.bottomsheets.input.InputSheet
import com.maxkeppeler.bottomsheets.input.type.InputEditText
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.MainActivityBinding
import org.bandev.buddhaquotes.fragments.FragmentAdapter

/**
 * Main is the main page of Buddha Quotes
 *
 * It has a ViewPager and allows the user to scroll between its fragments.
 * It uses [FragmentAdapter] as a fragment adapter and
 * https://github.com/Droppers/AnimatedBottomBar for its nice bottom bar.
 * @since v1.0.0
 * @author jack.txt & Fennec_exe
 */

class Main : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    /**
     * On activity created
     *
     * @param savedInstanceState [Bundle]
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourMain(this, window, resources)
        Languages().setLanguage(this)

        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()

        if (sharedPreferences.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            val i = Intent(this, Intro::class.java)
            startActivity(i)
        }

        // Setup view binding
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)

        // Setup viewPager with FragmentAdapter
        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.setCurrentItem(Store(this).fragment, false)
        binding.bottomBar.setupWithViewPager2(binding.viewPager)

        binding.bottomBar.setOnTabInterceptListener(object :
            AnimatedBottomBar.OnTabInterceptListener {
            override fun onTabIntercepted(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ): Boolean {
                Store(applicationContext).fragment = newIndex
                return true
            }
        })
    }

    /**
     * On options menu created
     * @param menu [Menu]
     * @return [Boolean]
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    /**
     * On options menu item selected
     * @param item [MenuItem]
     * @return [Boolean]
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                addToListSheet.show()
                true
            }
            R.id.settings -> {
                val intent = Intent(this, Settings::class.java)
                intent.putExtra("from", Activities.MAIN)
                this.startActivity(intent)
                finish()
                overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Build the input bottom sheet that allows creation of a new list
    val addToListSheet: InputSheet = InputSheet().build(this) {
        title("Create new list")
        with(InputEditText {
            required()
            hint("Insert name")
            changeListener { value ->
            } // Input value changes
            resultListener { value ->
                Lists().newList(value.toString(), requireContext())
            } // Input value changed when form finished
        })
        onNegative {
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }
        onPositive("Add") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }

            //Refresh fragments
            binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
            binding.viewPager.setCurrentItem(1, false)
            binding.bottomBar.setupWithViewPager2(binding.viewPager)
        }
    }
}

/*        var nameValue = "error"
        val dialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            cornerRadius(16f)
            title(R.string.lists_add_lists)
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

                Lists().newList(nameValue, context)

                //Refresh fragments
                binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
                binding.viewPager.setCurrentItem(1, false)
                binding.bottomBar.setupWithViewPager2(binding.viewPager)
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
                            (pref.getString("MASTER_LIST", "Favourites") ?: return).toLowerCase(
                                Locale.ROOT
                            ).split("//".toRegex()).toTypedArray()
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

        name.addTextChangedListener(watcher)*/