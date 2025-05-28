package org.bandev.buddhaquotes.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Pause
import org.bandev.buddhaquotes.ui.symbols.filled.PlayArrow
import org.bandev.buddhaquotes.ui.symbols.filled.Refresh
import org.bandev.buddhaquotes.ui.theme.ascenderHeight
import org.bandev.buddhaquotes.ui.theme.counterWidth
import org.bandev.buddhaquotes.ui.theme.descenderDepth
import org.bandev.buddhaquotes.ui.theme.lowercaseHeight
import org.bandev.buddhaquotes.ui.theme.thickStroke
import org.bandev.buddhaquotes.ui.theme.thinStroke
import org.bandev.buddhaquotes.ui.theme.uppercaseHeight

enum class PlayingState {
    Playing,
    Paused,
    Finished
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class)
@Composable
fun BreathingScreen() {
    var breathingPrompt by remember { mutableStateOf("Breathe in") }
    var playingState by remember { mutableStateOf(PlayingState.Playing) }
    var progress = remember { Animatable(0f) }
    var elapsedTime by remember { mutableLongStateOf(0L) } // in millis
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(40.dp), contentAlignment = Alignment.Center) {
            val thickStrokeWidth = with(LocalDensity.current) { 16.dp.toPx() }
            val thickStroke =
                remember(thickStrokeWidth) {
                    Stroke(
                        width = thickStrokeWidth,
                        cap = StrokeCap.Round
                    )
                }

            LaunchedEffect(playingState) {
                if (playingState == PlayingState.Playing) {
                    var lastFrameTimeNanos = withFrameNanos { it }

                    while (playingState == PlayingState.Playing && progress.value < 1f) {
                        val currentFrameTimeNanos = withFrameNanos { it }
                        val delta =
                            (currentFrameTimeNanos - lastFrameTimeNanos) / 1_000_000 // nanos to millis
                        lastFrameTimeNanos = currentFrameTimeNanos

                        elapsedTime += delta
                        progress.snapTo((elapsedTime.toFloat() / 10_000).coerceIn(0f, 1f))
                    }
                    if (progress.value == 1f) {
                        playingState = PlayingState.Finished
                        breathingPrompt = "Now slowly exhale"
                    }
                }
            }
            CircularWavyProgressIndicator(
                progress = { progress.value },
                modifier = Modifier.size(200.dp),
                stroke = thickStroke,
                trackStroke = thickStroke,
                wavelength = 50.dp,
                waveSpeed = 20.dp
            )

            IconButton(
                onClick = {
                    when (playingState) {
                        PlayingState.Finished -> {
                            scope.launch {
                                progress.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                                elapsedTime = 0L
                                playingState = PlayingState.Paused
                            }
                        }

                        PlayingState.Playing -> {
                            playingState = PlayingState.Paused
                        }

                        PlayingState.Paused -> {
                            playingState = PlayingState.Playing
                        }
                    }
                },
                shapes = IconButtonDefaults.shapes(),
                modifier = Modifier.size(72.dp)
            ) {
                val icon = when (playingState) {
                    PlayingState.Finished -> Symbols.Filled.Refresh
                    PlayingState.Paused -> Symbols.Filled.PlayArrow
                    PlayingState.Playing -> Symbols.Filled.Pause
                }
                Icon(
                    imageVector = icon,
                    contentDescription = icon.name,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        Text(
            text = breathingPrompt,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            minLines = 2,
            style = Typography().displayMedium.copy(
                fontFamily = FontFamily(
                    Font(
                        R.font.robotoflex_variable,
                        variationSettings = FontVariation.Settings(
                            FontVariation.opticalSizing(45.sp),
                            FontVariation.weight(350),
                            FontVariation.width(105f),
                            ascenderHeight(770f),
                            descenderDepth(-240),
                            thickStroke(92),
                            thinStroke(95),
                            counterWidth(600),
                            lowercaseHeight(520),
                            uppercaseHeight(725)
                        )
                    ),
                ),
                letterSpacing = 0.75.sp
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val playing = playingState == PlayingState.Playing
            ToggleButton(
                checked = playing,
                onCheckedChange = {
                    if (playingState == PlayingState.Finished) {
                        scope.launch {
                            progress.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                )
                            )
                            elapsedTime = 0L
                            playingState = PlayingState.Playing
                        }
                    } else if (it) {
                        playingState = PlayingState.Playing
                    } else {
                        playingState = PlayingState.Paused
                    }
                },
            ) {
                val label = if (playing) "Stop" else "Start"
                Icon(
                    if (playing) Symbols.Filled.Pause else Symbols.Filled.PlayArrow,
                    contentDescription = label
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label, style = MaterialTheme.typography.labelLargeEmphasized)
            }

            TextButton(
                onClick = {
                    scope.launch {
                        playingState = PlayingState.Paused
                        progress.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                        elapsedTime = 0L
                        playingState = PlayingState.Paused
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shapes = ButtonDefaults.shapes(),
            ) {
                Icon(Symbols.Filled.Refresh, contentDescription = "Restart")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Restart")
            }
        }
    }
}
