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

val Symbols.Filled.ArrowBack: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.ArrowBack",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveToRelative(313f, 520f)
            lineToRelative(196f, 196f)
            quadToRelative(12f, 12f, 11.5f, 28f)
            reflectiveQuadTo(508f, 772f)
            quadToRelative(-12f, 11f, -28f, 11.5f)
            reflectiveQuadTo(452f, 772f)
            lineTo(188f, 508f)
            quadToRelative(-6f, -6f, -8.5f, -13f)
            reflectiveQuadToRelative(-2.5f, -15f)
            quadToRelative(0f, -8f, 2.5f, -15f)
            reflectiveQuadToRelative(8.5f, -13f)
            lineToRelative(264f, -264f)
            quadToRelative(11f, -11f, 27.5f, -11f)
            reflectiveQuadToRelative(28.5f, 11f)
            quadToRelative(12f, 12f, 12f, 28.5f)
            reflectiveQuadTo(508f, 245f)
            lineTo(313f, 440f)
            horizontalLineToRelative(447f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(800f, 480f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(760f, 520f)
            lineTo(313f, 520f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledArrowBackPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.ArrowBack, contentDescription = null)
        }
    }
}
