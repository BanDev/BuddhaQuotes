package org.bandev.buddhaquotes.ui.symbols.outlined

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

val Symbols.Outlined.Cancel: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Outline.Cancel",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveToRelative(480f, 536f)
            lineToRelative(116f, 116f)
            quadToRelative(11f, 11f, 28f, 11f)
            reflectiveQuadToRelative(28f, -11f)
            quadToRelative(11f, -11f, 11f, -28f)
            reflectiveQuadToRelative(-11f, -28f)
            lineTo(536f, 480f)
            lineToRelative(116f, -116f)
            quadToRelative(11f, -11f, 11f, -28f)
            reflectiveQuadToRelative(-11f, -28f)
            quadToRelative(-11f, -11f, -28f, -11f)
            reflectiveQuadToRelative(-28f, 11f)
            lineTo(480f, 424f)
            lineTo(364f, 308f)
            quadToRelative(-11f, -11f, -28f, -11f)
            reflectiveQuadToRelative(-28f, 11f)
            quadToRelative(-11f, 11f, -11f, 28f)
            reflectiveQuadToRelative(11f, 28f)
            lineToRelative(116f, 116f)
            lineToRelative(-116f, 116f)
            quadToRelative(-11f, 11f, -11f, 28f)
            reflectiveQuadToRelative(11f, 28f)
            quadToRelative(11f, 11f, 28f, 11f)
            reflectiveQuadToRelative(28f, -11f)
            lineToRelative(116f, -116f)
            close()
            moveTo(480f, 880f)
            quadToRelative(-83f, 0f, -156f, -31.5f)
            reflectiveQuadTo(197f, 763f)
            quadToRelative(-54f, -54f, -85.5f, -127f)
            reflectiveQuadTo(80f, 480f)
            quadToRelative(0f, -83f, 31.5f, -156f)
            reflectiveQuadTo(197f, 197f)
            quadToRelative(54f, -54f, 127f, -85.5f)
            reflectiveQuadTo(480f, 80f)
            quadToRelative(83f, 0f, 156f, 31.5f)
            reflectiveQuadTo(763f, 197f)
            quadToRelative(54f, 54f, 85.5f, 127f)
            reflectiveQuadTo(880f, 480f)
            quadToRelative(0f, 83f, -31.5f, 156f)
            reflectiveQuadTo(763f, 763f)
            quadToRelative(-54f, 54f, -127f, 85.5f)
            reflectiveQuadTo(480f, 880f)
            close()
            moveTo(480f, 800f)
            quadToRelative(134f, 0f, 227f, -93f)
            reflectiveQuadToRelative(93f, -227f)
            quadToRelative(0f, -134f, -93f, -227f)
            reflectiveQuadToRelative(-227f, -93f)
            quadToRelative(-134f, 0f, -227f, 93f)
            reflectiveQuadToRelative(-93f, 227f)
            quadToRelative(0f, 134f, 93f, 227f)
            reflectiveQuadToRelative(227f, 93f)
            close()
            moveTo(480f, 480f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun OutlinedCancelPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Outlined.Cancel, contentDescription = null)
        }
    }
}
