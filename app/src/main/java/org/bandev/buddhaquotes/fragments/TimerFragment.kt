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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.duration.DurationSheet
import com.maxkeppeler.sheets.duration.DurationTimeFormat
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.TimerActivity
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.core.resolveColorAttr
import org.bandev.buddhaquotes.databinding.FragmentTimerBinding

/**
 * The timer where the meditation timer button is shown
 */
class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    internal val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.button.apply {
            setBackgroundColor(requireContext().resolveColorAttr(R.attr.colorPrimary))
            setOnClickListener {
                Feedback.virtualKey(it)
                it.isEnabled = false
                DurationSheet().show(requireContext()) {
                    title(R.string.meditation_timer)
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    format(DurationTimeFormat.MM_SS)
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
                    onClose { it.isEnabled = true }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.button.setBackgroundColor(requireContext().resolveColorAttr(R.attr.colorPrimary))
    }

    companion object {
        fun newInstance(position: Int): TimerFragment {
            return TimerFragment().apply {
                arguments = Bundle().apply { putInt("position", position) }
            }
        }
    }
}
