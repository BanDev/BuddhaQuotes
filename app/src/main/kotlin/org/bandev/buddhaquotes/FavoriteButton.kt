package org.bandev.buddhaquotes

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Favorite
import org.bandev.buddhaquotes.ui.symbols.outlined.Favorite
import org.bandev.buddhaquotes.ui.theme.heartTint

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        shapes = IconButtonDefaults.toggleableShapes()
    ) {
        EmbeddedFavouriteButton(checked = checked)
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun EmbeddedFavouriteButton(checked: Boolean) {
    val transition = updateTransition(checked, label = "Checked indicator")

    val tint by transition.animateColor(label = "Tint") { checked ->
        if (checked) heartTint else LocalContentColor.current
    }

    val size by transition.animateDp(
        transitionSpec = {
            if (false isTransitioningTo true) {
                keyframes {
                    durationMillis = 250
                    24.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                    28.dp at 15 using FastOutLinearInEasing // for 15-75 ms
                    32.dp at 75 // ms
                    28.dp at 150 // ms
                }
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Size"
    ) { 24.dp }

    Icon(
        imageVector = if (checked) Symbols.Filled.Favorite else Symbols.Outlined.Favorite,
        contentDescription = stringResource(R.string.favourite),
        tint = tint,
        modifier = Modifier.size(size)
    )
}