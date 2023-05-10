package org.bandev.buddhaquotes.items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.Offset

data class Heart(
    val position: Offset,
    val rotation: Float,
    val size: Animatable<Float, AnimationVector1D>,
    val alpha: Animatable<Float, AnimationVector1D>
)
