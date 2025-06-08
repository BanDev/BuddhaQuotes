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

val Symbols.Outlined.DarkMode: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Outline.DarkMode",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(480f, 840f)
            quadToRelative(-151f, 0f, -255.5f, -104.5f)
            reflectiveQuadTo(120f, 480f)
            quadToRelative(0f, -138f, 90f, -239.5f)
            reflectiveQuadTo(440f, 122f)
            quadToRelative(13f, -2f, 23f, 3.5f)
            reflectiveQuadToRelative(16f, 14.5f)
            quadToRelative(6f, 9f, 6.5f, 21f)
            reflectiveQuadToRelative(-7.5f, 23f)
            quadToRelative(-17f, 26f, -25.5f, 55f)
            reflectiveQuadToRelative(-8.5f, 61f)
            quadToRelative(0f, 90f, 63f, 153f)
            reflectiveQuadToRelative(153f, 63f)
            quadToRelative(31f, 0f, 61.5f, -9f)
            reflectiveQuadToRelative(54.5f, -25f)
            quadToRelative(11f, -7f, 22.5f, -6.5f)
            reflectiveQuadTo(819f, 481f)
            quadToRelative(10f, 5f, 15.5f, 15f)
            reflectiveQuadToRelative(3.5f, 24f)
            quadToRelative(-14f, 138f, -117.5f, 229f)
            reflectiveQuadTo(480f, 840f)
            close()
            moveTo(480f, 760f)
            quadToRelative(88f, 0f, 158f, -48.5f)
            reflectiveQuadTo(740f, 585f)
            quadToRelative(-20f, 5f, -40f, 8f)
            reflectiveQuadToRelative(-40f, 3f)
            quadToRelative(-123f, 0f, -209.5f, -86.5f)
            reflectiveQuadTo(364f, 300f)
            quadToRelative(0f, -20f, 3f, -40f)
            reflectiveQuadToRelative(8f, -40f)
            quadToRelative(-78f, 32f, -126.5f, 102f)
            reflectiveQuadTo(200f, 480f)
            quadToRelative(0f, 116f, 82f, 198f)
            reflectiveQuadToRelative(198f, 82f)
            close()
            moveTo(470f, 490f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun OutlinedDarkModePreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Outlined.DarkMode, contentDescription = null)
        }
    }
}
