package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.os.CountDownTimer
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
    lateinit var countdown_timer: CountDownTimer
    lateinit var timeSheet: TimeSheet
    var isRunning: Boolean = false
    var isPaused: Boolean = false
    var time_in_milli_seconds = 0L
    var duration = 0L


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timeSheet = TimeSheet().build(requireContext()) {
            title("Meditation timer")
            format(TimeFormat.MM_SS)
            onPositive { durationTimeInMillis: Long ->
                duration = durationTimeInMillis * 1000
                startTimer(duration)
            }
        }
        binding.button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else if (isPaused) {
                startTimer(time_in_milli_seconds)
            } else
                timeSheet.show()
        }
        binding.stop.setOnClickListener {
            countdown_timer.cancel()
            updateTextUI(0)
            binding.button.text = "Set Timer"
            isRunning = false
            isPaused = false
            binding.stop.visibility = View.INVISIBLE
        }
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {

            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI(time_in_milli_seconds)
            }
        }
        countdown_timer.start()

        isRunning = true
        binding.button.text = "Pause"
        binding.stop.visibility = View.VISIBLE
    }

    internal fun updateTextUI(time: Long) {
        val minute = (time / 1000) / 60
        val seconds = (time / 1000) % 60

        binding.timeLeft.text = "$minute:$seconds"
    }

    private fun pauseTimer() {
        binding.button.text = "Start"
        isPaused = true
        countdown_timer.cancel()
        isRunning = false
        binding.stop.visibility = View.VISIBLE
    }
}