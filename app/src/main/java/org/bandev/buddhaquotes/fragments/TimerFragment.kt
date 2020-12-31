package org.bandev.buddhaquotes.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maxkeppeler.bottomsheets.time_clock.TimeFormat
import com.maxkeppeler.bottomsheets.time_clock.TimeSheet
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
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
    internal val binding get() = _binding!!
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timeSheet: TimeSheet
    private var isRunning: Boolean = false
    private var isPaused: Boolean = false
    var timeInMilliSeconds: Long = 0L
    private var duration: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Builds the bottom sheet that allows for an input of time
        timeSheet = TimeSheet().build(requireContext()) {
            title("Meditation timer")
            format(TimeFormat.MM_SS)
            onNegative {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
            onPositive { durationTimeInMillis: Long ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
                duration = durationTimeInMillis * 1000
                startTimer(duration)
            }
        }

        binding.button.setOnClickListener {
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            when {
                isRunning -> {
                    pauseTimer()
                }
                isPaused -> {
                    startTimer(timeInMilliSeconds)
                }
                else -> timeSheet.show()
            }
        }

        binding.stop.setOnClickListener {
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            countDownTimer.cancel()
            updateTextUI(0)
            binding.button.text = "Set timer"
            isRunning = false
            isPaused = false
            binding.stop.visibility = View.INVISIBLE
        }
    }

    private fun startTimer(time_in_seconds: Long) {
        countDownTimer = object : CountDownTimer(time_in_seconds, 1000) {

            override fun onFinish() {
                // Shows a burst of confetti when the timer finishes
                binding.viewKonfetti.build()
                    .addColors(
                        Color.parseColor("#A864FD"),
                        Color.parseColor("#29CDFF"),
                        Color.parseColor("#78FF44"),
                        Color.parseColor("#FF718D"),
                        Color.parseColor("#FDFF6A")
                    )
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(
                        Shape.Square,
                        Shape.Circle
                    )
                    .addSizes(Size(10))
                    .setPosition(
                        binding.viewKonfetti.x + binding.viewKonfetti.width / 2,
                        binding.viewKonfetti.y + binding.viewKonfetti.height / 2
                    )
                    .burst(100)
            }

            // Updates the text of the timer every second
            override fun onTick(p0: Long) {
                timeInMilliSeconds = p0
                updateTextUI(timeInMilliSeconds)
            }
        }
        // Start the timer
        countDownTimer.start()
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
        countDownTimer.cancel()
        isRunning = false
        binding.stop.visibility = View.VISIBLE
    }
}