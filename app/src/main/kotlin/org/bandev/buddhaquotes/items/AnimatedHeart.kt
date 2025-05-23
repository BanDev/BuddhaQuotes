package org.bandev.buddhaquotes.items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.ui.theme.heartTint

data class Heart(
    val position: Offset,
    val rotation: Float,
    val size: Animatable<Float, AnimationVector1D>,
    val alpha: Animatable<Float, AnimationVector1D>
)

@Composable
fun AnimatedHeart(heart: Heart, onHeartAnimationEnd: () -> Unit = {}) {
    LaunchedEffect(heart) {
        heart.size.animateTo(
            targetValue = 100f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 500f)
        )
        heart.alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 100, easing = LinearEasing)
        )
        onHeartAnimationEnd()
    }

    Layout(
        content = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                tint = heartTint,
                modifier = Modifier
                    .size(heart.size.value.dp)
                    .graphicsLayer(
                        scaleX = heart.size.value / 100f,
                        scaleY = heart.size.value / 100f,
                        rotationZ = heart.rotation,
                        alpha = heart.alpha.value
                    )
            )
        },
        measurePolicy = { measurables, constraints ->
            val placeable = measurables.first().measure(constraints)
            layout(placeable.width, placeable.height) {
                placeable.place(
                    x = (heart.position.x - placeable.width / 2).toInt(),
                    y = (heart.position.y - placeable.height / 2).toInt()
                )
            }
        }
    )
}
