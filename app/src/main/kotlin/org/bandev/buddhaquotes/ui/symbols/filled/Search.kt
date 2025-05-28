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

val Symbols.Filled.Search: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Search",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(380f, 640f)
            quadToRelative(-109f, 0f, -184.5f, -75.5f)
            reflectiveQuadTo(120f, 380f)
            quadToRelative(0f, -109f, 75.5f, -184.5f)
            reflectiveQuadTo(380f, 120f)
            quadToRelative(109f, 0f, 184.5f, 75.5f)
            reflectiveQuadTo(640f, 380f)
            quadToRelative(0f, 44f, -14f, 83f)
            reflectiveQuadToRelative(-38f, 69f)
            lineToRelative(224f, 224f)
            quadToRelative(11f, 11f, 11f, 28f)
            reflectiveQuadToRelative(-11f, 28f)
            quadToRelative(-11f, 11f, -28f, 11f)
            reflectiveQuadToRelative(-28f, -11f)
            lineTo(532f, 588f)
            quadToRelative(-30f, 24f, -69f, 38f)
            reflectiveQuadToRelative(-83f, 14f)
            close()
            moveTo(380f, 560f)
            quadToRelative(75f, 0f, 127.5f, -52.5f)
            reflectiveQuadTo(560f, 380f)
            quadToRelative(0f, -75f, -52.5f, -127.5f)
            reflectiveQuadTo(380f, 200f)
            quadToRelative(-75f, 0f, -127.5f, 52.5f)
            reflectiveQuadTo(200f, 380f)
            quadToRelative(0f, 75f, 52.5f, 127.5f)
            reflectiveQuadTo(380f, 560f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledSearchPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.Search, contentDescription = null)
        }
    }
}
