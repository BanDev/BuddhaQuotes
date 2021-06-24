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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputCheckBox
import com.maxkeppeler.sheets.time.TimeFormat
import com.maxkeppeler.sheets.time.TimeSheet
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityTimerBinding

class TimerActivity : LocalizationActivity() {

    private lateinit var binding: ActivityTimerBinding
    private lateinit var notifBuilder: NotificationCompat.Builder
    private var durationTimeInMillis: Long = 0L
    private var durationTimeInSeconds: Long = 0L
    private var progressTimeInMillis: Long = 0L
    private var maxTime: String = "Error"
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var gong: MediaPlayer
    private var isPaused = false
    private var isRunning = false
    private lateinit var settings: Timer.Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = Timer().Settings(this)

        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)

        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = context.backIcon()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        with(binding.settings) {
            setBackgroundColor(context.resolveColorAttr(R.attr.colorPrimary))
            setOnClickListener {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                settingsSheet()
            }
        }

        durationTimeInMillis = (intent.extras ?: return).getLong("durationTimeInMillis")

        durationTimeInSeconds = durationTimeInMillis * 1000

        maxTime = convertToString(
            durationTimeInMillis / 60,
            durationTimeInMillis % 60,
            this
        )

        startTimer(durationTimeInSeconds)

        with(binding.pause) {
            setBackgroundColor(context.resolveColorAttr(R.attr.colorPrimary))
            setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }

                when {
                    isPaused -> resume()
                    isRunning -> pause()
                    else -> reset()
                }
            }
        }
    }

    private fun settingsSheet() {
        val vibrateSecondOriginal = settings.vibrateSecond
        val endSoundOriginal = settings.endSoundID
        val endNotificationOriginal = settings.showNotificaton
        val closeDrawable = IconicsDrawable(
            this,
            RoundedGoogleMaterial.Icon.gmr_expand_more
        ).apply {
            sizeDp = 24
            paddingDp = 6
        }
        InputSheet().show(this) {
            title("Timer Settings")
            closeIconButton(IconButton(closeDrawable))

            with(InputCheckBox {
                text(R.string.vibrate_second)
                defaultValue(settings.vibrateSecond)
                changeListener { settings.vibrateSecond = !settings.vibrateSecond }
            })

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
            onNegative(R.string.cancel) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                with(settings) {
                    vibrateSecond = vibrateSecondOriginal
                    endSoundID = endSoundOriginal
                    showNotificaton = endNotificationOriginal
                }
            }
            onPositive(R.string.okay) { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
        }
    }

    // Timer is done so must be for reset
    private fun reset() {
        val closeDrawable = IconicsDrawable(
            this,
            RoundedGoogleMaterial.Icon.gmr_expand_more
        ).apply {
            sizeDp = 24
            paddingDp = 6
        }
        TimeSheet().show(this) {
            title(R.string.meditation_timer)
            closeIconButton(IconButton(closeDrawable))
            format(TimeFormat.MM_SS)
            onNegative(R.string.cancel) { binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
            onPositive(R.string.okay) { durationTimeInMillis: Long ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }

                with(binding.pause) {
                    text = getString(R.string.pause)
                    setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_pause, 0, 0, 0
                    )
                }

                startTimer(durationTimeInMillis * 1000)
            }
        }
    }

    // Resume timer
    private fun resume() {
        // Change button text & icon
        with(binding.pause) {
            text = getString(R.string.pause)
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_pause, 0, 0, 0
            )
        }

        // Start the timer from the last recorded progress
        startTimer(progressTimeInMillis)

        isPaused = false
    }

    // Pause timer
    private fun pause() {
        // Change button text & icon
        with(binding.pause) {
            text = getString(R.string.play)
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_play, 0, 0, 0
            )
        }

        countDownTimer.cancel()

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
                isRunning = false

                if (settings.showNotificaton) {
                    NotificationManagerCompat.from(applicationContext).cancel(0)
                }

                with(binding.pause) {
                    text = getString(R.string.reset)
                    setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_refresh, 0, 0, 0
                    )
                }

                gong.start()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }

            override fun onTick(p0: Long) {
                updateTextUI(p0)

                if (settings.vibrateSecond) {
                    binding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                }

                progressTimeInMillis = p0
            }
        }

        isRunning = true

        countDownTimer.start()

        if (settings.showNotificaton) {
            buildNotification(this)
            pushNotification(this)
        }

        gong = MediaPlayer.create(applicationContext, R.raw.gong)
    }

    internal fun updateTextUI(time: Long) {
        // Calculate the minutes and seconds to show
        val minute = "%02d".format((time / 1000) / 60)
        val seconds = "%02d".format((time / 1000) % 60)

        // Update the notification text and progress
        if (settings.showNotificaton) {
            with(notifBuilder) {
                setContentTitle(getString(R.string.meditating_for) + " $maxTime")
                setContentText("$minute:$seconds")
                setProgress(
                    durationTimeInMillis.toInt(),
                    durationTimeInMillis.toInt() - time.toInt() / 1000,
                    false
                )
            }
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

    fun convertToString(minutes: Long, seconds: Long, context: Context): String {
        return if (minutes > 0) {
            // There is at least a minute
            if (seconds == 0L) {
                // There are no seconds e.g. exactly 5 mins
                "$minutes " + context.getString(R.string.minutes)
            } else {
                // There is at least a second
                "$minutes " + context.getString(R.string.minutes) + " " + context.getString(R.string.and) + " $seconds" + context.getString(
                    R.string.seconds
                )
            }
        } else {
            // There is less than a minute
            "$seconds " + context.getString(R.string.seconds)
        }
    }

    private fun pushNotification(context: Context) {
        NotificationManagerCompat.from(context).apply { notify(0, notifBuilder.build()) }
    }

    override fun onBackPressed() {
        countDownTimer.cancel()
        NotificationManagerCompat.from(applicationContext).cancel(0)
        super.onBackPressed()
    }
}
