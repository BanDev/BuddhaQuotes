package org.bandev.buddhaquotes.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WellbeingScreen() {
    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        val progress = rememberInfiniteTransition()
        val animation by progress.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(20_000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
        )

        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Box(Modifier.fillMaxSize()) {
                CircularWavyProgressIndicator(
                    progress = { animation },
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center),
                    stroke = Stroke(
                        width = with(LocalDensity.current) {
                            15.dp.toPx()
                        },
                        cap = StrokeCap.Round
                    ),
                    wavelength = 50.dp,
                    waveSpeed = 20.dp
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CircularWavyProgressIndicator(progress = { 0.5f })
        }
    }
}
