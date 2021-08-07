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
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputCheckBox
import com.maxkeppeler.sheets.time.TimeFormat
import com.maxkeppeler.sheets.time.TimeSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Accent.setAccentColour
import org.bandev.buddhaquotes.core.Insets.NAVIGATION_BARS
import org.bandev.buddhaquotes.core.Insets.STATUS_BARS
import org.bandev.buddhaquotes.core.Insets.applyInsets
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
    private lateinit var timerSettings: Prefs.Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAccentColour()
        with(window) {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            setDarkStatusIcons()
        }
        setDecorFitsSystemWindows(window, false)

        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timerSettings = Prefs(this).Timer()

        binding.toolbar.apply {
            setSupportActionBar(this)
            applyInsets(STATUS_BARS)
            setNavigationOnClickListener { onBackPressed() }
        }

        durationTimeInMillis = (intent.extras ?: return).getLong("durationTimeInMillis")

        durationTimeInSeconds = durationTimeInMillis * 1000

        maxTime = convertToString(
            durationTimeInMillis / 60,
            durationTimeInMillis % 60,
            this
        )

        startTimer(durationTimeInSeconds)

        binding.pause.apply {
            applyInsets(NAVIGATION_BARS)
            setBackgroundColor(context.resolveColorAttr(R.attr.colorPrimary))
            setOnClickListener {
                Feedback.confirm(it)
                when {
                    isPaused -> {
                        it.apply {
                            text = getString(R.string.pause)
                            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0)
                        }
                        startTimer(progressTimeInMillis)
                        isPaused = false
                    }
                    isRunning -> {
                        it.apply {
                            text = getString(R.string.play)
                            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0)
                        }

                        countDownTimer.cancel()
                        if (timerSettings.showNotificaton) {
                            notifBuilder.setContentTitle(
                                getString(R.string.meditating_for) + " $maxTime " + getString(
                                    R.string.has_been_paused
                                )
                            )
                            pushNotification(context)
                        }
                        isPaused = true
                    }
                    else -> {
                        it.isEnabled = false
                        TimeSheet().show(context) {
                            title(R.string.meditation_timer)
                            closeIconButton(IconButton(R.drawable.ic_down_arrow))
                            format(TimeFormat.MM_SS)
                            onNegative(R.string.cancel) { Feedback.virtualKey(binding.root) }
                            onPositive(R.string.okay) { durationTimeInMillis: Long ->
                                Feedback.confirm(binding.root)
                                it.apply {
                                    text = getString(R.string.pause)
                                    setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.ic_pause,
                                        0,
                                        0,
                                        0
                                    )
                                }

                                startTimer(durationTimeInMillis * 1000)
                            }
                            onClose { it.isEnabled = true }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meditate_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        InputSheet().show(this) {
            title("Timer Settings")
            closeIconButton(IconButton(R.drawable.ic_down_arrow))
            displayPositiveButton(false)
            displayNegativeButton(false)
            with(InputCheckBox {
                text("Play Gong at end")
                defaultValue(timerSettings.endSound)
                changeListener { timerSettings.endSound = !timerSettings.endSound }
            })
            with(InputCheckBox {
                text(R.string.vibrate_second)
                defaultValue(timerSettings.vibrateSecond)
                changeListener { timerSettings.vibrateSecond = !timerSettings.vibrateSecond }
            })
            with(InputCheckBox {
                text(R.string.show_notification)
                defaultValue(timerSettings.showNotificaton)
                changeListener {
                    timerSettings.showNotificaton = !timerSettings.showNotificaton
                    if (!timerSettings.showNotificaton) {
                        NotificationManagerCompat.from(applicationContext).cancel(0)
                    } else {
                        buildNotification(requireContext())
                        pushNotification(requireContext())
                    }
                }
            })
        }
        return true
    }

    private fun startTimer(timeInSeconds: Long) {

        countDownTimer = object : CountDownTimer(timeInSeconds, 1000) {

            override fun onFinish() {
                isRunning = false

                if (timerSettings.showNotificaton) {
                    NotificationManagerCompat.from(applicationContext).cancel(0)
                }

                binding.pause.apply {
                    text = getString(R.string.reset)
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_refresh, 0, 0, 0)
                }

                if (timerSettings.endSound) gong.start()

                Feedback.confirm(binding.root)
            }

            override fun onTick(p0: Long) {
                updateTextUI(p0)

                if (timerSettings.vibrateSecond) Feedback.clockTick(binding.root)

                progressTimeInMillis = p0
            }
        }

        isRunning = true

        countDownTimer.start()

        if (timerSettings.showNotificaton) {
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
        if (timerSettings.showNotificaton) {
            notifBuilder.apply {
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
        binding.timerText.text = getString(R.string.timer_text, minute, seconds)
    }

    private fun buildNotification(context: Context) {
        notifBuilder = NotificationCompat.Builder(context, "BQ.Timer").apply {
            setContentTitle(getString(R.string.meditating_for) + " $maxTime")
            setContentText(getString(R.string.time_left))
            setSmallIcon(R.drawable.ic_meditate)
            priority = NotificationCompat.PRIORITY_LOW
            setCategory(NotificationCompat.CATEGORY_PROGRESS)
            setOnlyAlertOnce(true)
            setSilent(true)
            setOngoing(false)
            setShowWhen(false)
            color = context.resolveColorAttr(R.attr.colorPrimary)
        }
    }

    private fun convertToString(minutes: Long, seconds: Long, context: Context): String {
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

    override fun onDestroy() {
        countDownTimer.cancel()
        NotificationManagerCompat.from(applicationContext).cancel(0)
        super.onDestroy()
    }
}
