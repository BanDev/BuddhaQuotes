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

val Symbols.Outlined.Attribution: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Outlined.Attribution",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(480f, 340f)
            quadToRelative(-53f, 0f, -81.5f, 14.5f)
            reflectiveQuadTo(370f, 396f)
            verticalLineToRelative(164f)
            quadToRelative(0f, 8f, 6f, 14f)
            reflectiveQuadToRelative(14f, 6f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(130f)
            quadToRelative(0f, 21f, 14.5f, 35.5f)
            reflectiveQuadTo(480f, 760f)
            quadToRelative(21f, 0f, 35.5f, -14.5f)
            reflectiveQuadTo(530f, 710f)
            verticalLineToRelative(-130f)
            horizontalLineToRelative(40f)
            quadToRelative(8f, 0f, 14f, -6f)
            reflectiveQuadToRelative(6f, -14f)
            verticalLineToRelative(-164f)
            quadToRelative(0f, -27f, -28.5f, -41.5f)
            reflectiveQuadTo(480f, 340f)
            close()
            moveTo(480f, 880f)
            quadToRelative(-82f, 0f, -155f, -31.5f)
            reflectiveQuadToRelative(-127.5f, -86f)
            quadTo(143f, 708f, 111.5f, 635f)
            reflectiveQuadTo(80f, 480f)
            quadToRelative(0f, -83f, 31.5f, -155.5f)
            reflectiveQuadToRelative(86f, -127f)
            quadTo(252f, 143f, 325f, 111.5f)
            reflectiveQuadTo(480f, 80f)
            quadToRelative(83f, 0f, 155.5f, 31.5f)
            reflectiveQuadToRelative(127f, 86f)
            quadToRelative(54.5f, 54.5f, 86f, 127f)
            reflectiveQuadTo(880f, 480f)
            quadToRelative(0f, 82f, -31.5f, 155f)
            reflectiveQuadToRelative(-86f, 127.5f)
            quadToRelative(-54.5f, 54.5f, -127f, 86f)
            reflectiveQuadTo(480f, 880f)
            close()
            moveTo(480f, 800f)
            quadToRelative(133f, 0f, 226.5f, -93.5f)
            reflectiveQuadTo(800f, 480f)
            quadToRelative(0f, -133f, -93.5f, -226.5f)
            reflectiveQuadTo(480f, 160f)
            quadToRelative(-133f, 0f, -226.5f, 93.5f)
            reflectiveQuadTo(160f, 480f)
            quadToRelative(0f, 133f, 93.5f, 226.5f)
            reflectiveQuadTo(480f, 800f)
            close()
            moveTo(480f, 320f)
            quadToRelative(26f, 0f, 43f, -17f)
            reflectiveQuadToRelative(17f, -43f)
            quadToRelative(0f, -26f, -17f, -43f)
            reflectiveQuadToRelative(-43f, -17f)
            quadToRelative(-26f, 0f, -43f, 17f)
            reflectiveQuadToRelative(-17f, 43f)
            quadToRelative(0f, 26f, 17f, 43f)
            reflectiveQuadToRelative(43f, 17f)
            close()
            moveTo(480f, 480f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun OutlinedAttributionPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Outlined.Attribution, contentDescription = null)
        }
    }
}
