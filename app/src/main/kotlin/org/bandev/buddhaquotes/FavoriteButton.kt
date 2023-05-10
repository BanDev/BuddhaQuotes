package org.bandev.buddhaquotes

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.ui.theme.heartTint

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onClick: () -> Unit = {}
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = { onClick() },
        modifier = modifier
    ) {
        val transition = updateTransition(checked, label = "Checked indicator")

        val tint by transition.animateColor(label = "Tint") { isChecked ->
            if (isChecked) heartTint else LocalContentColor.current
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        24.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        28.dp at 15 with FastOutLinearInEasing // for 15-75 ms
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
            imageVector = if (checked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}