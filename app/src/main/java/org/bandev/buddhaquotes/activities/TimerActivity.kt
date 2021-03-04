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

package org.bandev.buddhaquotes.activities

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputCheckBox
import com.maxkeppeler.sheets.time.TimeFormat
import com.maxkeppeler.sheets.time.TimeSheet
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.typeface.library.octicons.Octicons
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.core.Timer
import org.bandev.buddhaquotes.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {

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
    private lateinit var settings: Timer.Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get an instance of settings
        settings = Timer().Settings(this)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup da view
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mute.setImageDrawable(
            IconicsDrawable(
                this,
                Octicons.Icon.oct_mute
            ).apply {
                colorInt = ContextCompat.getColor(this@TimerActivity, R.color.textColorPrimary)
                sizeDp = 24
            })

        binding.posture.setImageDrawable(
            IconicsDrawable(
                this,
                Octicons.Icon.oct_person
            ).apply {
                colorInt = ContextCompat.getColor(this@TimerActivity, R.color.textColorPrimary)
                sizeDp = 24
            })

        binding.breathe.setImageDrawable(
            IconicsDrawable(
                this,
                Octicons.Icon.oct_heart
            ).apply {
                colorInt = ContextCompat.getColor(this@TimerActivity, R.color.textColorPrimary)
                sizeDp = 24
            })

        // On settings click
        binding.settings.setOnClickListener {
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            settingsSheet()
        }

        // Get the duration (mili seconds) of the timer
        durationTimeInMillis = (intent.extras ?: return).getLong("durationTimeInMillis")

        // Work out the other things we now know
        durationTimeInS = durationTimeInMillis * 1000

        // Work out a lovely string version of the maximum time
        maxTime = Timer().convertToString(
            durationTimeInMillis / 60,
            durationTimeInMillis % 60,
            this
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

            when {
                isPaused -> {
                    // Resume timer
                    resume()
                }
                isRunning -> {
                    // Pause timer
                    pause()
                }
                else -> {
                    // Reset the timer once it has finished
                    reset()
                }
            }
        }
    }

    private fun settingsSheet() {
        val vibrateSecondOriginal = settings.vibrateSecond
        val endSoundOriginal = settings.endSoundID
        val endNotificationOriginal = settings.showNotificaton
        val sounds = mutableListOf("Chime", "No")
        InputSheet().show(this) {
            title("Timer Settings")

            // Vibrate every second?
            with(InputCheckBox {
                text(R.string.vibrate_second)
                defaultValue(settings.vibrateSecond)
                changeListener { settings.vibrateSecond = !settings.vibrateSecond }
            })

            // Show notification?
            with(InputCheckBox {
                text(R.string.show_notification)
                defaultValue(settings.showNotificaton)
                changeListener {
                    settings.showNotificaton = !settings.showNotificaton
                    if (!settings.showNotificaton) {
                        NotificationManagerCompat.from(applicationContext).cancel(0)
                    } else {
                        buildNotification(requireContext())
                        pushNotification(requireContext())
                    }
                }
            })
            onNegative {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                // Reset their settings if they cancel the sheet
                settings.vibrateSecond = vibrateSecondOriginal
                settings.endSoundID = endSoundOriginal
                settings.showNotificaton = endNotificationOriginal
            }
            onPositive { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
        }
    }

    // Timer is done so must be for reset
    private fun reset() {
        TimeSheet().show(this) {
            title(R.string.meditation_timer)
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
                binding.pause.text = getString(R.string.pause)
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
        if (settings.showNotificaton) {
            notifBuilder.setContentTitle(
                getString(R.string.meditating_for) + " $maxTime " + getString(
                    R.string.has_been_paused
                )
            )
            pushNotification(this)
        }

        isPaused = true
    }

    private fun startTimer(timeInSeconds: Long) {

        countDownTimer = object : CountDownTimer(timeInSeconds, 1000) {

            override fun onFinish() {
                // Set is running to false
                isRunning = false

                // Remove the notification
                if (settings.showNotificaton) {
                    NotificationManagerCompat.from(applicationContext).cancel(0)
                }

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

                if (settings.vibrateSecond) {
                    // Vibrate the phone sorta but its nice
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                }

                // Save the time for pause
                progressTimeInMillis = p0
            }
        }

        // Set isRunning to true
        isRunning = true

        // Start the timer
        countDownTimer.start()

        // Make the notification so the user can leave the app and do something else
        if (settings.showNotificaton) {
            buildNotification(this)
            pushNotification(this)
        }

        // Load the gong
        gong = MediaPlayer.create(applicationContext, R.raw.gong)
    }

    internal fun updateTextUI(time: Long) {
        // Calculate the minutes and seconds to show
        val minute = "%02d".format((time / 1000) / 60)
        val seconds = "%02d".format((time / 1000) % 60)

        // Update the notification text and progress
        if (settings.showNotificaton) {
            notifBuilder.setContentTitle(getString(R.string.meditating_for) + " $maxTime")
            notifBuilder.setContentText("$minute:$seconds")
            notifBuilder.setProgress(
                durationTimeInMillis.toInt(),
                durationTimeInMillis.toInt() - time.toInt() / 1000,
                false
            )
            pushNotification(this)
        }

        // Show the minutes and seconds
        binding.timerText.text = "$minute:$seconds"
    }

    private fun buildNotification(context: Context) {
        notifBuilder = NotificationCompat.Builder(context, "BQ.Timer")
            .apply {
                setContentTitle(getString(R.string.meditating_for) + " $maxTime")
                setContentText(getString(R.string.time_left))
                setSmallIcon(R.drawable.nav_meditate)
                priority = NotificationCompat.PRIORITY_LOW
                setCategory(NotificationCompat.CATEGORY_PROGRESS)
                setOnlyAlertOnce(true)
                setSilent(true)
                setOngoing(false)
                setShowWhen(false)
            }
    }

    private fun pushNotification(context: Context) {
        // Push the notification
        NotificationManagerCompat.from(context).apply {
            notify(0, notifBuilder.build())
        }
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
