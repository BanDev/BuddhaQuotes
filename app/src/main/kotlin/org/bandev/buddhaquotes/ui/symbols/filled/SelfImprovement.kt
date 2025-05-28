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

val Symbols.Filled.SelfImprovement: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.SelfImprovement",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(272f, 800f)
            quadToRelative(-30f, 0f, -51f, -21f)
            reflectiveQuadToRelative(-21f, -51f)
            quadToRelative(0f, -21f, 12f, -39.5f)
            reflectiveQuadToRelative(32f, -26.5f)
            lineToRelative(156f, -62f)
            verticalLineToRelative(-90f)
            quadToRelative(-48f, 55f, -108f, 87.5f)
            reflectiveQuadTo(162f, 638f)
            quadToRelative(-17f, 2f, -29.5f, -9.5f)
            reflectiveQuadTo(120f, 600f)
            quadToRelative(0f, -17f, 11f, -29f)
            reflectiveQuadToRelative(28f, -14f)
            quadToRelative(55f, -8f, 101f, -34f)
            reflectiveQuadToRelative(84f, -71f)
            lineToRelative(54f, -64f)
            quadToRelative(12f, -14f, 28f, -21f)
            reflectiveQuadToRelative(34f, -7f)
            horizontalLineToRelative(40f)
            quadToRelative(18f, 0f, 34f, 7f)
            reflectiveQuadToRelative(28f, 21f)
            lineToRelative(54f, 64f)
            quadToRelative(38f, 45f, 84f, 71f)
            reflectiveQuadToRelative(101f, 34f)
            quadToRelative(17f, 2f, 28f, 14f)
            reflectiveQuadToRelative(11f, 29f)
            quadToRelative(0f, 17f, -12.5f, 28.5f)
            reflectiveQuadTo(798f, 638f)
            quadToRelative(-70f, -8f, -130f, -40.5f)
            reflectiveQuadTo(560f, 510f)
            verticalLineToRelative(90f)
            lineToRelative(156f, 62f)
            quadToRelative(20f, 8f, 32f, 26.5f)
            reflectiveQuadToRelative(12f, 39.5f)
            quadToRelative(0f, 30f, -21f, 51f)
            reflectiveQuadToRelative(-51f, 21f)
            lineTo(400f, 800f)
            verticalLineToRelative(-20f)
            quadToRelative(0f, -26f, 17f, -43f)
            reflectiveQuadToRelative(43f, -17f)
            horizontalLineToRelative(120f)
            quadToRelative(9f, 0f, 14.5f, -5.5f)
            reflectiveQuadTo(600f, 700f)
            quadToRelative(0f, -9f, -5.5f, -14.5f)
            reflectiveQuadTo(580f, 680f)
            lineTo(460f, 680f)
            quadToRelative(-42f, 0f, -71f, 29f)
            reflectiveQuadToRelative(-29f, 71f)
            verticalLineToRelative(20f)
            horizontalLineToRelative(-88f)
            close()
            moveTo(480f, 320f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(400f, 240f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(480f, 160f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(560f, 240f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(480f, 320f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledSelfImprovementPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.SelfImprovement, contentDescription = null)
        }
    }
}
