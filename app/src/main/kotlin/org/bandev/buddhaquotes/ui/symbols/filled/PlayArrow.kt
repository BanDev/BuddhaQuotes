package org.bandev.buddhaquotes.ui.symbols.filled

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

val Symbols.Filled.PlayArrow: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.PlayArrow",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(320f, 687f)
            verticalLineToRelative(-414f)
            quadToRelative(0f, -17f, 12f, -28.5f)
            reflectiveQuadToRelative(28f, -11.5f)
            quadToRelative(5f, 0f, 10.5f, 1.5f)
            reflectiveQuadTo(381f, 239f)
            lineToRelative(326f, 207f)
            quadToRelative(9f, 6f, 13.5f, 15f)
            reflectiveQuadToRelative(4.5f, 19f)
            quadToRelative(0f, 10f, -4.5f, 19f)
            reflectiveQuadTo(707f, 514f)
            lineTo(381f, 721f)
            quadToRelative(-5f, 3f, -10.5f, 4.5f)
            reflectiveQuadTo(360f, 727f)
            quadToRelative(-16f, 0f, -28f, -11.5f)
            reflectiveQuadTo(320f, 687f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledPlayArrowPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.PlayArrow, contentDescription = null)
        }
    }
}
