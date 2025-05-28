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

val Symbols.Filled.BookmarkHeart: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.BookmarkHeart",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(480f, 312f)
            quadToRelative(-12f, -15f, -31f, -23.5f)
            reflectiveQuadToRelative(-41f, -8.5f)
            quadToRelative(-36f, 0f, -62f, 26f)
            reflectiveQuadToRelative(-26f, 62f)
            quadToRelative(0f, 19f, 5.5f, 35f)
            reflectiveQuadToRelative(22.5f, 38f)
            quadToRelative(14f, 18f, 39f, 43f)
            reflectiveQuadToRelative(64f, 61f)
            quadToRelative(6f, 6f, 13.5f, 9f)
            reflectiveQuadToRelative(15.5f, 3f)
            quadToRelative(8f, 0f, 15.5f, -3f)
            reflectiveQuadToRelative(13.5f, -9f)
            quadToRelative(38f, -35f, 63f, -59.5f)
            reflectiveQuadToRelative(39f, -43.5f)
            quadToRelative(17f, -22f, 23f, -38.5f)
            reflectiveQuadToRelative(6f, -35.5f)
            quadToRelative(0f, -36f, -26f, -62f)
            reflectiveQuadToRelative(-62f, -26f)
            quadToRelative(-21f, 0f, -40.5f, 8.5f)
            reflectiveQuadTo(480f, 312f)
            close()
            moveTo(480f, 720f)
            lineTo(312f, 792f)
            quadToRelative(-40f, 17f, -76f, -6.5f)
            reflectiveQuadTo(200f, 719f)
            verticalLineToRelative(-519f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(280f, 120f)
            horizontalLineToRelative(400f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(760f, 200f)
            verticalLineToRelative(519f)
            quadToRelative(0f, 43f, -36f, 66.5f)
            reflectiveQuadToRelative(-76f, 6.5f)
            lineToRelative(-168f, -72f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledBookmarkHeartPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.BookmarkHeart, contentDescription = null)
        }
    }
}
