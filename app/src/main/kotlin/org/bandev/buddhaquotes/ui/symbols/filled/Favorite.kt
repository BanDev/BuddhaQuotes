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

val Symbols.Filled.Favorite: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Favorite",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(480f, 813f)
            quadToRelative(-14f, 0f, -28.5f, -5f)
            reflectiveQuadTo(426f, 792f)
            lineToRelative(-69f, -63f)
            quadToRelative(-106f, -97f, -191.5f, -192.5f)
            reflectiveQuadTo(80f, 326f)
            quadToRelative(0f, -94f, 63f, -157f)
            reflectiveQuadToRelative(157f, -63f)
            quadToRelative(53f, 0f, 100f, 22.5f)
            reflectiveQuadToRelative(80f, 61.5f)
            quadToRelative(33f, -39f, 80f, -61.5f)
            reflectiveQuadTo(660f, 106f)
            quadToRelative(94f, 0f, 157f, 63f)
            reflectiveQuadToRelative(63f, 157f)
            quadToRelative(0f, 115f, -85f, 211f)
            reflectiveQuadTo(602f, 730f)
            lineToRelative(-68f, 62f)
            quadToRelative(-11f, 11f, -25.5f, 16f)
            reflectiveQuadToRelative(-28.5f, 5f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledFavoritePreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.Favorite, contentDescription = null)
        }
    }
}
