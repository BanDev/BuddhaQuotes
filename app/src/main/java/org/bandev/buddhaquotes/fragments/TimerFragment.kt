package org.bandev.buddhaquotes.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.maxkeppeler.bottomsheets.time_clock.TimeFormat
import com.maxkeppeler.bottomsheets.time_clock.TimeSheet
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.Settings
import org.bandev.buddhaquotes.core.Activities
import org.bandev.buddhaquotes.core.TimerStore
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
    private var durationM: Long = 0L
    private var maxMin: Int = 0
    private var maxSec: Int = 0
    private lateinit var builder: NotificationCompat.Builder
    lateinit var mediaPlayer: MediaPlayer
    private var data = Bundle()

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
        if (TimerStore(requireContext()).resumeTimers()) {
            startTimer(TimerStore(requireContext()).progress)
        }
        // Builds the bottom sheet that allows for an input of time
        timeSheet = TimeSheet().build(requireContext()) {
            title("Meditation timer")
            closeButtonDrawable(R.drawable.ic_down_arrow)
            format(TimeFormat.MM_SS)
            onNegative { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
            onPositive { durationTimeInMillis: Long ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
                duration = durationTimeInMillis * 1000
                startTimer(duration)
                durationM = durationTimeInMillis

                maxMin = (durationTimeInMillis / 60).toInt()
                maxSec = (durationTimeInMillis % 60).toInt()
                data.putLong("duration", duration)
            }
        }

        mediaPlayer = MediaPlayer.create(context, R.raw.gong)
        mediaPlayer.setOnPreparedListener {
            print("End Media Loaded")
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
            resetTimer()
        }
    }

    internal fun resetTimer() {
        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        countDownTimer.cancel()
        binding.button.text = "Begin"
        isRunning = false
        isPaused = false
        binding.stop.visibility = View.INVISIBLE
        binding.textView4.visibility = View.VISIBLE
        binding.textView5.visibility = View.VISIBLE
        binding.timeLeft.visibility = View.INVISIBLE

        TimerStore(requireContext()).active = false

        NotificationManagerCompat.from(requireContext()).apply {
            cancel(12)
        }
    }

    private fun startTimer(timeInSeconds: Long) {

        //Store total time in miliseconds here
        TimerStore(requireContext()).duration = timeInSeconds
        TimerStore(requireContext()).active = true

        countDownTimer = object : CountDownTimer(timeInSeconds, 1000) {

            override fun onFinish() {
                // Stop the timer
                resetTimer()

                // Shows a burst of confetti when the timer finishes
                builder.setContentTitle("Meditating complete!")
                builder.setContentText("You meditated for $maxMin:$maxSec")
                NotificationManagerCompat.from(requireContext()).apply {
                    notify(12, builder.build())
                }
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
                mediaPlayer.start()
            }

            // Updates the text of the timer every second
            override fun onTick(p0: Long) {
                updateTextUI(p0)
            }
        }
        val sharedpreferences: SharedPreferences? =
            context?.getSharedPreferences("timer", Context.MODE_PRIVATE)

        val editor = sharedpreferences?.edit()

        (editor ?: return).putBoolean("new", false)
        editor.apply()

        // Start the timer
        countDownTimer.start()
        isRunning = true
        binding.textView4.visibility = View.INVISIBLE
        binding.textView5.visibility = View.INVISIBLE
        binding.timeLeft.visibility = View.VISIBLE
        binding.button.text = "Pause"
        binding.stop.visibility = View.VISIBLE

        builder = NotificationCompat.Builder(requireContext(), "BQ.Timer").apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.drawable.nav_meditate)
            priority = NotificationCompat.PRIORITY_LOW
            setCategory(NotificationCompat.CATEGORY_PROGRESS)
            setOnlyAlertOnce(true)
            setOngoing(true)
        }

        val progressMax = 100
        val progressCurrent = 0

        NotificationManagerCompat.from(requireContext()).apply {
            // Issue the initial notification with zero progress
            builder.setProgress(progressMax, progressCurrent, false)
            notify(12, builder.build())
        }
    }

    internal fun updateTextUI(time: Long) {
        timeInMilliSeconds = time
        val minute = (time / 1000) / 60
        val seconds = (time / 1000) % 60
        builder.setContentTitle("Meditating for $maxMin:$maxSec")
        builder.setContentText("Time left: $minute:$seconds")
        builder.setProgress(durationM.toInt(), time.toInt() / 1000, false)
        NotificationManagerCompat.from(requireContext()).apply {
            notify(12, builder.build())
        }
        binding.timeLeft.text = "$minute:$seconds"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(context, Settings::class.java)
                if (isRunning) {
                    pauseTimer()
                    TimerStore(requireContext()).progress = timeInMilliSeconds
                }
                intent.putExtra("from", Activities.MAIN)
                intent.putExtra("paused", isRunning)

                this.startActivity(intent)
                activity?.finish()
                activity?.overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pauseTimer() {
        binding.button.text = "Resume"
        isPaused = true
        countDownTimer.cancel()
        binding.stop.visibility = View.VISIBLE
        builder.setContentTitle("Meditating for $maxMin:$maxSec has been paused")
        NotificationManagerCompat.from(requireContext()).apply {
            notify(12, builder.build())
        }
    }
}