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

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maxkeppeler.sheets.time.TimeFormat
import com.maxkeppeler.sheets.time.TimeSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.Timer
import org.bandev.buddhaquotes.databinding.FragmentTimerBinding

/**
 * The timer where the meditiation timer button is shown
 */
class TimerFragment : Fragment() {
    companion object {
        fun newInstance(position: Int): TimerFragment {
            val instance =
                TimerFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    private var _binding: FragmentTimerBinding? = null
    internal val binding get() = _binding!!

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

        binding.button.setOnClickListener {
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            TimeSheet().show(requireContext()) {
                title(R.string.meditation_timer)
                format(TimeFormat.MM_SS)
                minTime(1)
                onNegative { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                onPositive { durationTimeInMillis: Long ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }

                    val sharedPrefs: SharedPreferences? =
                        requireContext().getSharedPreferences("timer", Context.MODE_PRIVATE)
                    val editor = sharedPrefs?.edit()
                    editor?.putBoolean("new", false)
                    editor?.apply()

                    val toTimer = Intent(context, Timer::class.java)
                    toTimer.putExtra("durationTimeInMillis", durationTimeInMillis)
                    startActivity(toTimer)
                }
            }
        }
    }
}
