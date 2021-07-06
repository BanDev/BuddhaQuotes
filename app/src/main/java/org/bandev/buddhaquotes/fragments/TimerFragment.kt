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

package org.bandev.buddhaquotes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import com.maxkeppeler.sheets.time.TimeFormat
import com.maxkeppeler.sheets.time.TimeSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.TimerActivity
import org.bandev.buddhaquotes.core.Event
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.core.Icons
import org.bandev.buddhaquotes.core.resolveColorAttr
import org.bandev.buddhaquotes.databinding.FragmentTimerBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * The timer where the meditiation timer button is shown
 */
class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    internal val binding get() = _binding!!
    private lateinit var icons: Icons
    private var toolbarMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        icons = Icons(requireContext())

        binding.button.setOnClickListener {
            Feedback.virtualKey(binding.root)
            timerSheet()
        }
    }

    @Subscribe
    fun onEventReceive(event: Event) {
        if (event is Event.StartMeditationTimer) {
            timerSheet()
            EventBus.getDefault().post(Event.RestoreDrawerButton)
        }
    }

    private fun timerSheet() {
        binding.button.isEnabled = false
        TimeSheet().show(requireContext()) {
            title(R.string.meditation_timer)
            closeIconButton(icons.closeSheet())
            format(TimeFormat.MM_SS)
            minTime(1)
            onNegative(R.string.cancel) { Feedback.virtualKey(binding.root) }
            onPositive(R.string.okay) { durationTimeInMillis: Long ->
                Feedback.confirm(binding.root)
                startActivity(
                    Intent(context, TimerActivity::class.java).putExtra(
                        "durationTimeInMillis",
                        durationTimeInMillis
                    )
                )
            }
            onClose { binding.button.isEnabled = true }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quote_menu, menu)
        toolbarMenu = menu
        menu.findItem(R.id.help).icon = icons.help()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.help -> {
                toolbarMenu?.findItem(R.id.help)?.isEnabled = false
                InfoSheet().show(requireContext()) {
                    title("title")
                    content("You can do this to meditate as well as doing this hi jack are you reading this")
                    closeIconButton(icons.closeSheet())
                    displayButtons(false)
                    withCoverLottieAnimation(LottieAnimation {
                        ratio(Ratio(2, 1))
                        setupAnimation {
                            setAnimation(R.raw.meditation)
                        }
                    })
                    onClose { toolbarMenu?.findItem(R.id.help)?.isEnabled = true }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.button.setBackgroundColor(requireContext().resolveColorAttr(R.attr.colorPrimary))
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(position: Int): TimerFragment {
            val instance = TimerFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }
}
