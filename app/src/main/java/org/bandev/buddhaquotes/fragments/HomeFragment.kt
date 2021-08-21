package org.bandev.buddhaquotes.fragments

import android.content.res.ColorStateList.valueOf
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputEditText
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.FragmentAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Bus
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), Bus.Listener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bus: Bus
    private lateinit var model: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ViewModel::class.java)

        bus = Bus(this, "HomeFragment").apply { listen() }

        binding.viewPager.adapter = FragmentAdapter(childFragmentManager, lifecycle)

        binding.createList.apply {
            backgroundTintList = valueOf(requireContext().resolveColorAttr(R.attr.colorAccent))
            onClick {
                it.isEnabled = false
                InputSheet().show(context) {
                    title(R.string.create_new_list)
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    with(
                        InputEditText {
                            required()
                            hint(R.string.insert_name)
                            resultListener { value ->
                                model.Lists().new(value.toString()) { list ->
                                    GlobalBus.post(Message(MessageType.NEW_LIST, list))
                                }
                            }
                        }
                    )
                    onNegative(R.string.cancel) { Feedback.virtualKey(requireView()) }
                    onPositive(R.string.add) { Feedback.confirm(requireView()) }
                    onClose { it.isEnabled = true }
                }
            }
        }

        binding.bottomBar.apply {
            applyInsets(InsetType.NAVIGATION_BARS)
            setupWithViewPager2(binding.viewPager)
            onTabSelected = {
                when (it) {
                    binding.bottomBar.tabs[Fragments.QUOTES.ordinal] -> {
                        bus.broadcast(Message(MessageType.NOTIFY_TAB_INDEX, Fragments.QUOTES))
                        binding.createList.hide()
                    }
                    binding.bottomBar.tabs[Fragments.LISTS.ordinal] -> {
                        bus.broadcast(Message(MessageType.NOTIFY_TAB_INDEX, Fragments.LISTS))
                        binding.createList.show()
                    }
                    binding.bottomBar.tabs[Fragments.MEDITATE.ordinal] -> {
                        bus.broadcast(Message(MessageType.NOTIFY_TAB_INDEX, Fragments.MEDITATE))
                        binding.createList.hide()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Prefs(requireContext()).Settings().bottomBar = binding.bottomBar.selectedIndex // Temporary fix to prevent bottom bar resetting on return
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            createList.backgroundTintList = valueOf(requireContext().resolveColorAttr(R.attr.colorAccent))
            bottomBar.apply {
                selectTabAt(Prefs(context).Settings().bottomBar, false) // Temporary fix to prevent bottom bar resetting on return
                tabColorSelected = requireContext().resolveColorAttr(R.attr.colorPrimary)
                indicatorColor = requireContext().resolveColorAttr(R.attr.colorPrimary)
            }
        }
    }

    override fun onMessageReceived(message: Message<*>) {
        if (message.type == MessageType.NOTIFY_BOTTOMBAR) binding.bottomBar.selectTabAt((message.data as Fragments).ordinal)
    }
}