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

val Symbols.Filled.BrightnessAuto: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.BrightnessAuto",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(312f, 640f)
            horizontalLineToRelative(64f)
            lineToRelative(16f, -46f)
            quadToRelative(8f, -21f, 31.5f, -33.5f)
            reflectiveQuadTo(505f, 548f)
            quadToRelative(22f, 0f, 39.5f, 12.5f)
            reflectiveQuadTo(570f, 594f)
            lineToRelative(9f, 27f)
            quadToRelative(3f, 8f, 10.5f, 13.5f)
            reflectiveQuadTo(606f, 640f)
            quadToRelative(15f, 0f, 23.5f, -12.5f)
            reflectiveQuadTo(633f, 601f)
            lineTo(519f, 299f)
            quadToRelative(-3f, -9f, -13.5f, -14f)
            reflectiveQuadToRelative(-36.5f, -5f)
            quadToRelative(-9f, 0f, -17f, 5f)
            reflectiveQuadToRelative(-11f, 14f)
            lineTo(312f, 640f)
            close()
            moveTo(426f, 496f)
            lineTo(478f, 346f)
            horizontalLineToRelative(4f)
            lineToRelative(52f, 150f)
            lineTo(426f, 496f)
            close()
            moveTo(346f, 800f)
            lineTo(240f, 800f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(160f, 720f)
            verticalLineToRelative(-106f)
            lineToRelative(-77f, -78f)
            quadToRelative(-11f, -12f, -17f, -26.5f)
            reflectiveQuadTo(60f, 480f)
            quadToRelative(0f, -15f, 6f, -29.5f)
            reflectiveQuadTo(83f, 424f)
            lineToRelative(77f, -78f)
            verticalLineToRelative(-106f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(240f, 160f)
            horizontalLineToRelative(106f)
            lineToRelative(78f, -77f)
            quadToRelative(12f, -11f, 26.5f, -17f)
            reflectiveQuadToRelative(29.5f, -6f)
            quadToRelative(15f, 0f, 29.5f, 6f)
            reflectiveQuadToRelative(26.5f, 17f)
            lineToRelative(78f, 77f)
            horizontalLineToRelative(106f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(800f, 240f)
            verticalLineToRelative(106f)
            lineToRelative(77f, 78f)
            quadToRelative(11f, 12f, 17f, 26.5f)
            reflectiveQuadToRelative(6f, 29.5f)
            quadToRelative(0f, 15f, -6f, 29.5f)
            reflectiveQuadTo(877f, 536f)
            lineToRelative(-77f, 78f)
            verticalLineToRelative(106f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(720f, 800f)
            lineTo(614f, 800f)
            lineToRelative(-78f, 77f)
            quadToRelative(-12f, 11f, -26.5f, 17f)
            reflectiveQuadTo(480f, 900f)
            quadToRelative(-15f, 0f, -29.5f, -6f)
            reflectiveQuadTo(424f, 877f)
            lineToRelative(-78f, -77f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledBrightnessAutoPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.BrightnessAuto, contentDescription = null)
        }
    }
}
