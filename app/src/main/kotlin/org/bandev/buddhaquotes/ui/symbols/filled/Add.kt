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

val Symbols.Filled.Add: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Add",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(440f, 520f)
            lineTo(240f, 520f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(200f, 480f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(240f, 440f)
            horizontalLineToRelative(200f)
            verticalLineToRelative(-200f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(480f, 200f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(520f, 240f)
            verticalLineToRelative(200f)
            horizontalLineToRelative(200f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(760f, 480f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(720f, 520f)
            lineTo(520f, 520f)
            verticalLineToRelative(200f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 760f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(440f, 720f)
            verticalLineToRelative(-200f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledAddPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.Add, contentDescription = null)
        }
    }
}
