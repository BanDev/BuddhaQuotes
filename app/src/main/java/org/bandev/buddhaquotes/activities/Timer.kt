package org.bandev.buddhaquotes.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.maxkeppeler.bottomsheets.options.Option
import com.maxkeppeler.bottomsheets.options.OptionsSheet
import com.maxkeppeler.bottomsheets.time_clock.TimeFormat
import com.maxkeppeler.bottomsheets.time_clock.TimeSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.GoodTime
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityTimerBinding
import kotlin.math.max

class Timer : AppCompatActivity() {

    private lateinit var binding: ActivityTimerBinding
    private lateinit var notifBuilder: NotificationCompat.Builder
    private var durationTimeInMillis: Long = 0L
    private var durationTimeInS: Long = 0L
    private var progressTimeInMillis: Long = 0L
    private var maxTime: String = "Error"
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var gong: MediaPlayer
    private var isPaused = false
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourWhite(this, window, resources)
        Languages().setLanguage(this)

        // Setup da view
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the duration (mili seconds) of the timer
        durationTimeInMillis = (intent.extras ?: return).getLong("durationTimeInMillis")

        // Work out the other things we now know
        durationTimeInS = durationTimeInMillis * 1000

        // Work out a lovely string version of the maximum time
        maxTime = GoodTime().convertToString(
            durationTimeInMillis / 60,
            durationTimeInMillis % 60
        )

        // Begin the timer
        startTimer(durationTimeInS)

        // Handle the people that want to leave
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        // When some geezer presses Pause
        binding.pause.setOnClickListener {
            // Nice haptic feedback. I like!!!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }

            if (isPaused) {
                // Resume timer
                resume()
            } else if (isRunning) {
                // Pause timer
                pause()
            } else {
                // Timer is done so must be for reset
                reset()
            }
        }

    }

    // Timer is done so must be for reset
    private fun reset() {
        TimeSheet().show(this) {
            title(R.string.meditation_timer)
            closeButtonDrawable(R.drawable.ic_down_arrow)
            format(TimeFormat.MM_SS)
            onNegative { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
            onPositive { durationTimeInMillis: Long ->
                // Nice Clicky thing
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }

                // Change button text & icon
                binding.pause.text = this.getString(R.string.pause)
                binding.pause.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_pause, 0, 0, 0
                )

                // Restart timer now
                startTimer(durationTimeInMillis * 1000)

            }
        }
    }

    // Resume timer
    private fun resume() {
        // Change button text & icon
        binding.pause.text = getString(R.string.pause)
        binding.pause.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_pause, 0, 0, 0
        )

        // Start the timer from the last recorded progress
        startTimer(progressTimeInMillis)

        isPaused = false
    }

    // Pause timer
    private fun pause() {
        // Change button text & icon
        binding.pause.text = getString(R.string.play)
        binding.pause.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_play, 0, 0, 0
        )

        // Stop the timer
        countDownTimer.cancel()

        // Update notification
        notifBuilder.setContentTitle(getString(R.string.meditating_for) + " $maxTime " + getString(R.string.has_been_paused))
        NotificationManagerCompat.from(this).apply {
            notify(0, notifBuilder.build())
        }

        isPaused = true
    }

    private fun startTimer(timeInSeconds: Long) {

        countDownTimer = object : CountDownTimer(timeInSeconds, 1000) {

            override fun onFinish() {
                // Set is running to false
                isRunning = false

                // Remove the notification
                NotificationManagerCompat.from(applicationContext).cancel(0)

                // Change button text & icon
                binding.pause.text = getString(R.string.reset)
                binding.pause.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_refresh, 0, 0, 0
                )

                // Play the gong
                gong.start()

                // Haptic feedback
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }

            // Updates the text of the timer every second
            override fun onTick(p0: Long) {
                updateTextUI(p0)

                // Vibrate the phone sorta but its nice
                binding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)

                // Save the time for pause thingy
                progressTimeInMillis = p0
            }
        }

        // Set is running to true
        isRunning = true

        // Start the timer
        countDownTimer.start()

        // Make the notification so the user can leave the app and do something else
        notifBuilder = NotificationCompat.Builder(this, "BQ.Timer")
            .apply {
                setContentTitle(getString(R.string.meditating_for) + " $maxTime")
                setContentText(getString(R.string.time_left))
                setSmallIcon(R.drawable.nav_meditate)
                priority = NotificationCompat.PRIORITY_LOW
                setCategory(NotificationCompat.CATEGORY_PROGRESS)
                setOnlyAlertOnce(true)
                setOngoing(true)
            }

        // Push the notification
        NotificationManagerCompat.from(this).apply {
            // Issue the initial notification with zero progress
            notifBuilder.setProgress(0, 0, false)
            notify(0, notifBuilder.build())
        }

        // Load the gong
        gong = MediaPlayer.create(applicationContext, R.raw.gong)
    }

    internal fun updateTextUI(time: Long) {
        // Calculate the minutes and seconds to show
        val minute = "%02d".format((time / 1000) / 60)
        val seconds = "%02d".format((time / 1000) % 60)

        // Update the notification text and progress
        notifBuilder.setContentTitle(getString(R.string.meditating_for) + " $maxTime")
        notifBuilder.setContentText("$minute:$seconds")
        notifBuilder.setProgress(durationTimeInMillis.toInt(), time.toInt() / 1000, false)
        NotificationManagerCompat.from(this).apply {
            notify(0, notifBuilder.build())
        }

        //Show the minutes and seconds
        binding.timerText.text = "$minute:$seconds"
    }

    // User has given up, they want to do something else
    override fun onBackPressed() {
        // Stop the timer & remove notification
        countDownTimer.cancel()
        NotificationManagerCompat.from(applicationContext).cancel(0)

        // Finish the activity
        finish()
    }

}