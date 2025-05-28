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

val Symbols.Filled.Pause: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Pause",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(640f, 760f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(560f, 680f)
            verticalLineToRelative(-400f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(640f, 200f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(720f, 280f)
            verticalLineToRelative(400f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(640f, 760f)
            close()
            moveTo(320f, 760f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(240f, 680f)
            verticalLineToRelative(-400f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(320f, 200f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(400f, 280f)
            verticalLineToRelative(400f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(320f, 760f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledPausePreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.Pause, contentDescription = null)
        }
    }
}
