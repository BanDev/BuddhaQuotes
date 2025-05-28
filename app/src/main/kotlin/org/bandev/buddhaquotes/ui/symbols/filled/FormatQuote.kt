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

val Symbols.Filled.FormatQuote: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.FormatQuote",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveToRelative(262f, 660f)
            lineToRelative(58f, -100f)
            quadToRelative(-66f, 0f, -113f, -47f)
            reflectiveQuadToRelative(-47f, -113f)
            quadToRelative(0f, -66f, 47f, -113f)
            reflectiveQuadToRelative(113f, -47f)
            quadToRelative(66f, 0f, 113f, 47f)
            reflectiveQuadToRelative(47f, 113f)
            quadToRelative(0f, 23f, -5.5f, 42.5f)
            reflectiveQuadTo(458f, 480f)
            lineTo(331f, 700f)
            quadToRelative(-5f, 9f, -14f, 14.5f)
            reflectiveQuadToRelative(-20f, 5.5f)
            quadToRelative(-23f, 0f, -34.5f, -20f)
            reflectiveQuadToRelative(-0.5f, -40f)
            close()
            moveTo(622f, 660f)
            lineTo(680f, 560f)
            quadToRelative(-66f, 0f, -113f, -47f)
            reflectiveQuadToRelative(-47f, -113f)
            quadToRelative(0f, -66f, 47f, -113f)
            reflectiveQuadToRelative(113f, -47f)
            quadToRelative(66f, 0f, 113f, 47f)
            reflectiveQuadToRelative(47f, 113f)
            quadToRelative(0f, 23f, -5.5f, 42.5f)
            reflectiveQuadTo(818f, 480f)
            lineTo(691f, 700f)
            quadToRelative(-5f, 9f, -14f, 14.5f)
            reflectiveQuadToRelative(-20f, 5.5f)
            quadToRelative(-23f, 0f, -34.5f, -20f)
            reflectiveQuadToRelative(-0.5f, -40f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledFormatQuotePreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.FormatQuote, contentDescription = null)
        }
    }
}
