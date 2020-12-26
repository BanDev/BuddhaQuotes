package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maxkeppeler.bottomsheets.time_clock.TimeFormat
import com.maxkeppeler.bottomsheets.time_clock.TimeSheet
import org.bandev.buddhaquotes.databinding.FragmentTimerBinding

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
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TimeSheet().show(requireContext()) {
            title("Meditation timer")
            format(TimeFormat.MM_SS)
            onPositive { durationTimeInMillis: Long ->
                // Handle selected time
            }
        }
    }
}