package org.bandev.buddhaquotes

import android.graphics.Matrix
import android.graphics.Shader
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun WavesLoadingIndicator(modifier: Modifier, color: Color, progress: Float) {
    BoxWithConstraints(modifier = modifier.offset(y = 16.dp), contentAlignment = Alignment.Center) {
        val constraintsWidth = this.maxWidth
        val constraintsHeight = this.maxHeight
        val density = LocalDensity.current

        val wavesShader by produceState<Shader?>(
            initialValue = null,
            constraintsHeight,
            constraintsWidth,
            color,
            density
        ) {
            value = withContext(Dispatchers.Default) {
                createWavesShader(
                    width = with(density) { constraintsWidth.roundToPx() },
                    height = with(density) { constraintsHeight.roundToPx() },
                    color = color
                )
            }
        }

        if (progress > 0f && wavesShader != null) {
            WavesOnCanvas(shader = wavesShader!!, progress = progress.coerceAtMost(0.99f))
        }
    }
}

@Composable
private fun WavesOnCanvas(shader: Shader, progress: Float) {
    val matrix = remember { Matrix() }

    val paint = remember(shader) {
        Paint().apply {
            isAntiAlias = true
            this.shader = shader
        }
    }

    val wavesTransition = rememberWavesTransition()
    val amplitudeRatio by wavesTransition.amplitudeRatio
    val waveShiftRatio by wavesTransition.waveShiftRatio

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas {
            val height = size.height
            val width = size.width
            matrix.setScale(1f, amplitudeRatio / AmplitudeRatio, 0f, WaterLevelRatio * height)
            matrix.postTranslate(waveShiftRatio * width, (WaterLevelRatio - progress) * height)
            shader.setLocalMatrix(matrix)
            it.drawRect(0f, 0f, width, height, paint)
        }
    }
}

private class WavesTransition(
    val waveShiftRatio: State<Float>,
    val amplitudeRatio: State<Float>
)

@Composable
private fun rememberWavesTransition(): WavesTransition {
    val transition = rememberInfiniteTransition()

    val waveShiftRatio = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = WavesShiftAnimationDurationInMillis,
                easing = LinearEasing
            )
        )
    )

    val amplitudeRatio = transition.animateFloat(
        initialValue = 0.005f,
        targetValue = 0.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = WavesAmplitudeAnimationDurationInMillis,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    return remember(transition) {
        WavesTransition(waveShiftRatio = waveShiftRatio, amplitudeRatio = amplitudeRatio)
    }
}

@Stable
private fun createWavesShader(width: Int, height: Int, color: Color): Shader {
    val angularFrequency = 2f * PI / width
    val amplitude = height * AmplitudeRatio
    val waterLevel = height * WaterLevelRatio

    val bitmap = ImageBitmap(width = width, height = height, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)

    val wavePaint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }

    val waveY = FloatArray(size = width + 1)

    wavePaint.color = color.copy(alpha = 0.3f)
    for (beginX in 0..width) {
        val wx = beginX * angularFrequency
        val beginY = waterLevel + amplitude * sin(wx).toFloat()
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = beginY),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePaint
        )
        waveY[beginX] = beginY
    }

    wavePaint.color = color
    val endX = width + 1
    val waveToShift = width / 4
    for (beginX in 0..width) {
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = waveY[(beginX + waveToShift).rem(endX)]),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePaint
        )
    }
    return ImageShader(image = bitmap, tileModeX = TileMode.Repeated, tileModeY = TileMode.Clamp)
}

private const val AmplitudeRatio = 0.05f
private const val WaterLevelRatio = 0.5f
private const val WavesShiftAnimationDurationInMillis = 2500
private const val WavesAmplitudeAnimationDurationInMillis = 3000