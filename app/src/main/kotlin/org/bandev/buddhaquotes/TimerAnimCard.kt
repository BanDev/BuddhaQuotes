package org.bandev.buddhaquotes

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.duration.DurationView
import com.maxkeppeler.sheets.duration.models.DurationSelection
import kotlin.math.roundToInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.ui.theme.SpringDefaultDampingRatio
import org.bandev.buddhaquotes.ui.theme.SpringDefaultStiffness
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Timer(
    modifier: Modifier,
    timerDurationInMillis: Int,
    onTimerDurationInMillisChange: (Int) -> Unit,
    isTimerStarted: Boolean,
    onTimerIsStartedChange: (Boolean) -> Unit,
    timerProgress: Float,
) {
    val cardHorizontalPadding by animateDpAsState(
        targetValue = if (isTimerStarted) 48.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = SpringDefaultDampingRatio,
            stiffness = SpringDefaultStiffness
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = cardHorizontalPadding)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Crossfade(
                targetState = isTimerStarted,
                animationSpec = tween(durationMillis = if (isTimerStarted) 0 else 350),
                modifier = Modifier.animateContentHeight(),
                label = ""
            ) { isTimerStartedCurrentValue ->
                val durationState = rememberUseCaseState(embedded = false)
                if (!isTimerStartedCurrentValue) {
                    DurationView(
                        useCaseState = durationState,
                        selection = DurationSelection(
                            onNegativeClick = { durationState.invokeReset() },
                            onPositiveClick = { milliseconds ->
                                onTimerDurationInMillisChange((milliseconds * 1000).toInt())
                                onTimerIsStartedChange(!isTimerStarted)
                            }
                        )
                    )
                } else {
                    TimerCountdown(
                        timerProgress = timerProgress,
                        timerDurationInMillis = timerDurationInMillis
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            if (isTimerStarted) {
                StartCancelTimerButton(
                    isTimerStarted = isTimerStarted,
                    enabled = timerDurationInMillis > 0
                ) {
                    onTimerIsStartedChange(!isTimerStarted)
                }
            }
        }
    }
}

@Composable
private fun TimerCountdown(timerProgress: Float, timerDurationInMillis: Int) {
    val style = remember {
        TextStyle(fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)
    }
    val text = remember(timerProgress, timerDurationInMillis) {
        val seconds = (timerProgress * (timerDurationInMillis / 1000)).roundToInt()
        "%02d : %02d".format(Locale.ROOT, seconds / 60, seconds % 60)
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        style = style,
        text = text
    )
}

@Composable
private fun StartCancelTimerButton(isTimerStarted: Boolean, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        border = ButtonDefaults.outlinedButtonBorder(isTimerStarted),
        colors = if (!isTimerStarted) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
        elevation = if (isTimerStarted) null else ButtonDefaults.buttonElevation()
    ) {
        Text(text = if (!isTimerStarted) "START" else "CANCEL")
    }
}

private fun Modifier.animateContentHeight(): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "animateContentSize"
    }
) {
    val scope = rememberCoroutineScope()
    val animModifier = remember(scope) { HeightAnimationModifier(scope) }
    this
        .clipToBounds()
        .then(animModifier)
}

private class HeightAnimationModifier(val scope: CoroutineScope) : LayoutModifier {
    val animSpec: AnimationSpec<Int> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    private data class AnimData(
        val anim: Animatable<Int, AnimationVector1D>,
        var startHeight: Int
    )

    private var animData: AnimData? = null

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val placeable = measurable.measure(constraints)

        val measuredSize = IntSize(placeable.width, placeable.height)
        val width = measuredSize.width
        val height = animateTo(measuredSize.height)
        return layout(width, height) {
            placeable.placeRelative(0, 0)
        }
    }

    fun animateTo(targetHeight: Int): Int {
        val data = animData?.apply {
            if (targetHeight != anim.targetValue) {
                startHeight = anim.value
                scope.launch {
                    anim.animateTo(targetHeight, animSpec)
                }
            }
        } ?: AnimData(Animatable(targetHeight, Int.VectorConverter, 1), targetHeight)
        animData = data
        return data.anim.value
    }
}